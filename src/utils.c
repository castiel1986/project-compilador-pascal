/*+----------------------------------------------------------+
  |          T A B E L A    D E   S I M B O L O S              |
  |                                                            |
  |   Por Luiz Eduardo da Silva          JUNHO - 2004          |
  +----------------------------------------------------------+*/

/*+----------------------------------------------------------+
  | Definicao dos limites das estruturas da tab. de simbolos |
  +----------------------------------------------------------+*/
#define TAM_TSIMB 100 /* Tamanho da tabela de simbolos */
#define TAM_PSEMA 100 /* Tamanho da pilha semantica */

/*+----------------------------------------------------------+
  | Definicao dos tipos enumerados utilizados no compilador  |
  +----------------------------------------------------------+*/
enum cat_tipo  {interv};  /* usado com os NIVEL_CORR tipos basicos integer, boolean */
            
/*----- Categoria dos elementos da TABELA DE SIMBOLOS -----*/                      
enum categoria {cat_var,    /* categoria variavel       */
                cat_par,    /* categoria parametro      */
                cat_pro,    /* categoria procedimento   */
                cat_fun,    /* categoria funcao         */
                cat_rot,    /* categoria rotulo         */
                cat_tip,    /* categoria tipo           */
                cat_cte,    /* categoria constante      */
                pre_read,   /* cat. pre'-definido READ  */
                pre_write}; /* cat. pre'-definido WRITE */
                
/*----- Classe de mecanismo de passagem de parametro -----*/                
enum mecanismo {pas_end,    /* passagem por endereco (referencia) */
                pas_val};   /* passagem por valor                 */
                
/*----- Categoria dos elementos da PILHA SEMANTICA -----*/                
enum cat_pilha {c_marca,    /* categoria marca                            */
                c_ident,    /* categoria identificador                    */
                c_param,    /* categoria parametro                        */
                c_nrent,    /* categoria numero de entrada                */
                c_nvloc,    /* nro. de pos. ocupadas por variaveis locais */
                c_nivelcor,
                //c_nvglo,    /* nro. de pos. ocupadas por variaveis globais */
                c_nparm,    /* nro. de posicoes ocupadas pelos parametros */
                c_rotul,    /* categoria rotulo                           */
                c_dtipo,    /* categoria descritor de tipo                */
                c_numro,    /* categoria numero (qualquer)                */
                c_mecan,    /* categoria mecanismo                        */
                c_opera,    /* categoria operador                         */
                c_lspar,    /* categoria lista de parametros              */
                c_pread,    /* categoria variavel global pre-read         */
                c_pwrit,    /* categoria variavel global pre-write        */
                c_qualquer};/* categoria qualquer (coringa)               */

/*------ Enumeracao dos operadores --------*/                
enum operador  {op_mais,        /* operador mais +         */
                op_menos,       /* operador menos -        */
                op_or,          /* operador or             */
                op_vezes,       /* operador *              */
                op_div,         /* operador div            */
                op_and,         /* operador and            */
                op_igual,       /* operador igual =        */
                op_diferente,   /* operador diferente <>   */
                op_menor,       /* operador menor <        */
                op_maior,       /* operador maior >        */
                op_meigual,     /* operador menor igual <= */
                op_maigual};    /* operador maior igual >= */



/*+----------------------------------------------------------+
  |        Declaracao das variaveis globais do programa        |
  +----------------------------------------------------------+*/
int TOPO_TSIMB      = 0; /* Var. global de TOPO da tabela de simbolos    */
int TOPO_PSEMA      = 0; /* Var. global de TOPO da pilha semantica       */
int NRO_ENTRADAS   = 0;  /* Var. global de Numero de Entradas            */
int NRO_POS_LOCAIS = 0; /* Numero de Posicoes ocupadas por var. locais   */
int NRO_ROTINAS = 0;    /* Numero de rotinas do programa */ 
int POS_PARAM  = 0;  /* Posicao de um parametro na rotina */
int NIVEL_CORR     = 0;  /* Nivel corrente */
int ROTULO          = 0; /* Proximo numero de rotulo                     */
int CONTA_VARS      = 0; /* conta o numero de variaveis declaradas       */
int PRE_READ       = 0;  /* Indica que esta sendo compilado um READ      */
int PRE_WRITE      = 0;  /* Indica que esta sendo compilado um WRITE     */
int PRE_ROTINA     = 0;  
int POS_SIMB;             /* Posicao de um simbolo na tabela de simbolos */
int NIVEL_POS_SIMB;       /* Nível de um simbolo na tabela de simbolos   */
char NOME_PROC[30];       /* Nome do procedimento */
int aux, aux1, cont;                  /* variavel do tipo inteira auxiliar */
int numero_da_linha = 1; /* numero da linha no programa pascal */
char atomo[30];           /* nome de um identificador ou numero */

FILE *arq_tab,           /* arquivo com valores interm. da TABELA SIMBOLOS */
     *arq_pil,           /* arquivo com valores interm. da PILHA SEMANTICA */
     *arq_mep;           /* arquivo com codigo MEPA gerado da compilacao   */


/*+----------------------------------------------------------+
  | Definicao da estrutura DESCRITOR DE TIPO        +-----+  |
  |     cat    tam limite-inf limite-sup tipo-elem  |     |  |
  |    +------+---+----------+----------+-----------|-+   |  |
  |    |interv| 1 | -32768   |  +32767  |           * |<--+  |
  |    +------+---+----------+----------+-------------+      |
  +----------------------------------------------------------+*/
struct desc_tipo {
  enum cat_tipo cat;
  int tam;
  int limite_inf;
  int limite_sup;
  /*struct desc_tipo *tipo_elem; */
} *TIPO_INT;

/*+----------------------------------------------------------+
  | Definicao da estrutura LISTA DE PARAMETROS               |
  |       mec tipo     mec tipo     mec tipo                 |
  |      +---+----+   +---+----+   +---+----+                |
  |  --->|   | ---+-->|   | ---+-->|   |  / |                |
  |      +---+----+   +---+----+   +---+----+                |
  +----------------------------------------------------------+*/
struct param {
  enum mecanismo mec;
  struct desc_tipo *tipo;
};

struct listapar {
  enum mecanismo mec;
  struct desc_tipo *tipo;
  struct listapar *prox;
} *PROX_PARAM = NULL;      /* variavel global utilizada para pegar */
                           /* o proximo parametro de uma rotina    */

/*+----------------------------------------------------------+
  | Definicao da estrutura TABELA DE SIMBOLOS                |
  |    Os elementos tem uma parte comum e uma parte variavel |
  | que depende do elemento armazenado na tabela de simbolos:|
  |          id   cat    nivel     info                      |
  |        +----+-----+---------+----------+                 |
  |        |    |     |         |          |                 |
  |        +----+-----+---------+----------+                 |
  |                               variavel                   |
  |  A parte variavel para cada elemento e':                 |
  |     var (variavel) - deslocamento e tipo                 |
  |     par (parametro) - deslocamento, tipo e mecanismo     |
  |     proc (procedimento) - rotulo interno, lista de para- |
  |                           metros.                        |
  |     func (funcao) - rotulo interno, lista de parametros, |
  |                     tipo do resultado e deslocamento.    |
  |     rot (rotulo) - rotulo interno, definido (flag)       |
  |     tipo (tipo) - tipo (descritor de tipo)               |
  |     cte (constante) - tipo e valor.                      |
  +----------------------------------------------------------+*/

struct elem_tab_simbolos {
  char id[30];
  enum categoria cat;
  int nivel;
  union {
   struct {int desloca; struct desc_tipo *tipo;} var;
   struct {int desloca; struct desc_tipo *tipo; enum mecanismo mec;} par;
   struct {int rot_int; struct listapar *pars;} proc;
   struct {int rot_int; struct listapar *pars; int desloca;} func;
   struct {int rot_int; int definido;} rot;
   struct {struct desc_tipo *tipo;} tipo;
   struct {struct desc_tipo *tipo; int valor;} cte;
 } info;

} TSIMB [TAM_TSIMB], elem_tab;

/*+----------------------------------------------------------+
  | Definicao da estrutura PILHA SEMANTICA                   |
  |   Varios elementos diferentes podem ser armazenados na   |
  | pilha semantica. Para sua facil identificacao existe um  |
  | campo categoria para diferencia-los.                     |
  +----------------------------------------------------------+*/
struct elem_pil_semantica {
  enum cat_pilha cat;
  union {
    enum operador op;            /* operador                          */
    enum mecanismo mec;          /* mecanismo                         */
    char marca;                  /* marca                             */
    char id[30];                 /* identificador                     */
    int nro_entradas;            /* numero de entradas                */
    int nro_vlocais;             /* numero de posicoes locais         */
    int nivel_corrente;
    //int nro_vglobais;          /* numero de posicoes globais        */
    int nro_param;               /* numero de posicoes dos parametros */
    int rotulo;                  /* rotulo                            */
    struct param par;            /* parametro (mecanismo, tipo)       */
    int numero;                  /* numero                            */
    struct desc_tipo *tipo;      /* descritor de tipo                 */
    struct listapar *prox_param; /* proximo parametro da rotina       */
    int pre_read;                /* variavel global pre-read          */
    int pre_write;               /* variavel global pre-write         */
  } info;
} PSEMA [TAM_PSEMA], elem_pilha;

/*+----------------------------------------------------------+
  | Rotina geral de MENSAGEM DE ERRO -                         |
  | Na execucao desta rotina, uma mensagem de erro identifi-|
  | cando o nome do programa, a linha do erro e o erro encon-|
  | trado e' apresentada. Esta rotina trabalha com numero va-|
  | riavel de parametros, porque algumas mensagens de erro |
  | fazem referencia aos nomes de identificadores usados no |
  | programa. Ao final todos os arquivos abertos pelo compi- |
  | lador sao fechados e a compilacao e' abortada.             |
  +----------------------------------------------------------+*/
void msg_erro (char *msg, ...) {
  char formato [255];
  va_list arg;
  va_start (arg, msg);
  sprintf (formato, "\n%d: ", numero_da_linha);
  strcat (formato, msg);
  strcat (formato, "\n\n");
  printf ("\nERRO no programa");
  vprintf (formato, arg);
  va_end (arg);
  exit (1);
}

/*+----------------------------------------------------------+
  | Funcao que BUSCA um simbolo na tabela de simbolos.         |
  |      Retorna -1 se o simbolo nao esta' na tabela           |
  |      Retorna i, onde i e' o indice do simbolo na tabela    |
  |                  se o simbolo esta' na tabela              |
                                                               |
  +----------------------------------------------------------+*/
//Alterado
int busca_simbolo (char* ident)
{
  int i = TOPO_TSIMB-1;
  for (; i >= 0; i--){
     if ( (!strcmp (TSIMB[i].id, ident)) && (TSIMB[i].nivel <= NIVEL_CORR) )
        return i;
  }
  return -1;
}

int busca_nome_proc (char* ident)
{
  int i = TOPO_TSIMB-1;
  for (; i >= 0; i--){
     if ( !strcmp (TSIMB[i].id, ident) )
        return i;
  }
  return -1;
}

/*+----------------------------------------------------------+
  | Funcao que INSERE um simbolo na tabela de simbolos.       |
  |     Se ja' existe um simbolo com mesmo nome e mesmo nivel |
  |     uma mensagem de erro e' apresentada e o programa e' |
  |     interrompido.                                         |
  +----------------------------------------------------------+*/
//Verificado
void insere_simbolo (struct elem_tab_simbolos *elem)
{
  if (TOPO_TSIMB == TAM_TSIMB) {
      msg_erro ("OVERFLOW - tabela de simbolos");
  }
  else {
      POS_SIMB = busca_simbolo (elem->id);
      //printf("variavel: %s - nivel: %d - nivelCorrente: %d\n", elem->id, elem->nivel, NIVEL_CORR);
      if ((POS_SIMB != -1) && (TSIMB [POS_SIMB].nivel == elem->nivel)){
         msg_erro ("Identificador [%s] ja' declarado", elem->id);
      }
      TSIMB [TOPO_TSIMB] = *elem;
      TOPO_TSIMB++;
  }
}

/*+----------------------------------------------------------+
  | Funcao que ELIMINA n simbolos da tabela de simbolos.      |
  +----------------------------------------------------------+*/
void elimina_simbolos (int n)
{
  if ((TOPO_TSIMB - n) < 0) {
      msg_erro ("UNDERFLOW - tabela de simbolos");
  }
  else
      TOPO_TSIMB -= n;
}

/*+----------------------------------------------------------+
  | Funcao de insercao de uma variavel na tabela de simbolos |
  +----------------------------------------------------------+*/
//Alterado
void insere_variavel (char *ident) {
    strcpy (elem_tab.id, ident);
    elem_tab.cat = cat_var;
    elem_tab.nivel = NIVEL_CORR;
    elem_tab.info.var.desloca = CONTA_VARS;
    insere_simbolo (&elem_tab);
}

void insere_parametro (char *ident, int desloca) {
    //printf("parametro: %s - desloca: %d\n", ident, desloca);
    strcpy (elem_tab.id, ident);
    elem_tab.cat = c_param;
    elem_tab.nivel = NIVEL_CORR;
    elem_tab.info.var.desloca = desloca;
    insere_simbolo (&elem_tab);
}

void insere_procedimento(char *ident){
    strcpy (elem_tab.id, ident);
    elem_tab.cat = cat_pro;
    elem_tab.nivel = NIVEL_CORR;
    elem_tab.info.proc.rot_int = ROTULO;
    insere_simbolo (&elem_tab);
}

void insere_funcao(char *ident){
    strcpy (elem_tab.id, ident);
    elem_tab.cat = cat_fun;
    elem_tab.nivel = NIVEL_CORR;
    elem_tab.info.func.rot_int = ROTULO;
    insere_simbolo (&elem_tab);
}

/*+----------------------------------------------------------+
  | Rotina que EMPILHA UM ELEMENTO NA PILHA SEMANTICA        |
  | A unica verificacao que e' feita nesta rotina e' com re- |
  | lacao ao tamanho que foi estipulado para o vetor da pilha|
  | semantica.                                               |
  +----------------------------------------------------------+*/
void empilha (struct elem_pil_semantica elem)
{
  if (TOPO_PSEMA == TAM_PSEMA) {
     msg_erro ("OVERFLOW - pilha semantica");
  }
  else {
     PSEMA [TOPO_PSEMA] = elem;
     TOPO_PSEMA++;
  }
}


/*+----------------------------------------------------------+
  | Rotina que DESEMPILHA UM ELEMENTO DA PILHA               |
  |   Para forcar a correspondencia entre o valor na pilha e |
  | o valor esperado da pilha, cada rotina que desempilha de-|
  | ve definir a categoria do elemento que se espera no topo.|
  | Se nao for correspondente, uma mensagem de erro e' apre- |
  | sentada.                                                 | 
  +----------------------------------------------------------+*/
//Alterado
void desempilha (enum cat_pilha cat, struct elem_pil_semantica *elem)
{
  char nome[30];
  if (TOPO_PSEMA == 0) {
     msg_erro ("UNDERFLOW - pilha semantica");
  }
  else {
     TOPO_PSEMA--;
     *elem = PSEMA [TOPO_PSEMA];
     if (elem->cat != cat && cat != c_qualquer) {
        switch (cat) {
          case c_marca: strcpy (nome, "MARCA");
                        break;
          case c_ident: strcpy (nome, "IDENTIFICADOR");
                        break;
          case c_param: strcpy (nome, "PARAMETRO");
                        break;
          case c_nrent: strcpy (nome, "NUMERO DE ENTRADAS");
                        break;
          case c_nvloc: strcpy (nome, "NUMERO VARIAVEIS LOCAIS");
                        break;
          case c_nparm: strcpy (nome, "NUMERO DE PARAMETROS");
                        break;
          case c_rotul: strcpy (nome, "ROTULO");
                        break;
          case c_dtipo: strcpy (nome, "DESCRITOR DE TIPO");
                        break;
          case c_numro: strcpy (nome, "NUMERO");
                        break;
          case c_mecan: strcpy (nome, "MECANISMO DE PASSAGEM");
                        break;
          case c_opera: strcpy (nome, "OPERACAO");
                        break;
          case c_lspar: strcpy (nome, "PROXIMO PARAMETRO");
                        break;
          case c_pread: strcpy (nome, "VARIAVEL PRE' READ");
                        break;
          case c_pwrit: strcpy (nome, "VARIAVEL PRE' WRITE");
                        break;
          case c_nivelcor: strcpy (nome, "NIVEL CORRENTE");
                        break;
        }
        msg_erro ("O elemento na pilha nao e' %s", nome);
     }
  }
}

/*+----------------------------------------------------------+
  | Rotina que EMPILHA IDENTIFICADOR                         |
  |   Empilha um identificador generico. Nome de rotina, de  |
  | variavel, etc...                                         |
  +----------------------------------------------------------+*/
void empilha_identificador (void) {
  elem_pilha.cat = c_ident;
  strcpy (elem_pilha.info.id, atomo);
  empilha (elem_pilha);
}

/*+----------------------------------------------------------+
  | Rotina que EMPILHA ROTULO                                |
  |   O empilhamento de rotulo e' necessario para possibili- |
  | tar a colocacao de instrucoes de desvios coerentes para  |
  | os comandos IF, WHILE  e rotinas, etc...                 |
  +----------------------------------------------------------+*/
void empilha_rotulo (void) {
  elem_pilha.cat = c_rotul;
  elem_pilha.info.rotulo = ROTULO;
  empilha (elem_pilha);
}

/*+----------------------------------------------------------+
  | Rotina que EMPILHA MECANISMO                             |
  +----------------------------------------------------------+*/
void empilha_mecanismo (enum mecanismo mec) {
  elem_pilha.cat = c_mecan;
  elem_pilha.info.mec = mec;
  empilha (elem_pilha);
}

/*+----------------------------------------------------------+
  | Rotina que EMPILHA UMA MARCA DE INICIO DE LISTA          |
  |   Esta rotina e' utilizada para compilacao das declara-  |
  | coes de variaveis e rotinas (procedimentos e funcoes).Es-|
  | ta marca define o inicio da declaracao.                  |
  +----------------------------------------------------------+*/
void marca_inicio_lista (char c) {
  elem_pilha.cat = c_marca;
  elem_pilha.info.marca = c;
  empilha (elem_pilha); 
}

void empilha_num_val(void){
  elem_pilha.cat = c_nvloc;
  elem_pilha.info.nro_vlocais = CONTA_VARS;
  empilha(elem_pilha);
}

void empilha_nivel_corrente(void){
  elem_pilha.cat = c_nivelcor;
  elem_pilha.info.nivel_corrente = NIVEL_CORR;
  empilha(elem_pilha);
}

/*+----------------------------------------------------------+
  | Rotina que EMPILHA PROXIMO PARAMETRO                     |
  |   O proximo parametro de uma rotina deve ser empilhado   |
  | para possibilitar a chamada de uma funcao como parametro |
  | de chamada de uma outra funcao.                          |
  +----------------------------------------------------------+*/
void empilha_prox_parametro (void) {
  elem_pilha.cat = c_lspar;
  elem_pilha.info.prox_param = PROX_PARAM;
  empilha (elem_pilha);
}


/*+----------------------------------------------------------+
  | Rotina de INSERCAO NA LISTA DE PARAMETROS -              |
  |   Para cada procedimento ou funcao encontrado no progra- |
  | ma que esta' sendo compilado, e' montada uma lista enca- |
  | deada (ligada). Esta lista contem o mecanismo e o  tipo  |
  | de cada parametro da rotina. Assim:                      |
  |                          MEC TIP     MEC TIP   ...       |
  | apontador na            +---+---+   +---+---+            |
  | entrada da rotina ----->|   |  ---->|   |  ---->         |
  | na tab. simbolos        +---+---+   +---+---+            |
  +----------------------------------------------------------+*/
void insere_listapar (struct listapar **pars, 
                      enum mecanismo mec)
{
  struct listapar *aux;
  if (!(*pars)) {
     *pars = malloc (sizeof (struct listapar));
     (*pars)->mec  = mec;
     (*pars)->prox = NULL;
  }
  else {
     aux = *pars;
     while (aux->prox)
       aux = aux->prox;
     aux->prox = malloc (sizeof (struct listapar));
     aux->prox->mec  = mec;
     aux->prox->prox = NULL;
  }
}

void escreve_lista_param(struct listapar **pars){ 
  
   struct listapar *aux;
   aux = *pars;
   while (aux){
       printf("mecanismo: %d\n", aux->mec);
       aux = aux->prox;
   }
}

int retorna_mec(int pos, struct listapar **pars){
   struct listapar *aux;
   aux = *pars;
     
   while (aux && (pos > 0 )){
       pos--;
       aux = aux->prox;
   }

   if(aux->mec == pas_end){
       return 0; 
   }else{
       return 1;
   }
}

void abreArquivoEscrita(char* nomeArquivo){
	if ((arq_mep = fopen (nomeArquivo, "w")) == NULL){
		puts ("Arquivo nao pode ser aberto");
		exit (1);
	}
}

