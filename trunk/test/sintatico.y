%{
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdarg.h>

#include "utils.c"
#include "lexico.c"
%}

%expect 1
%start programa

%token S_FUNCTION
%token S_PROCEDURE
%token S_DIFERENTE
%token S_DOISPTOS
%token S_FECHAPAR
%token S_MAIGUAL
%token S_MEIGUAL
%token S_PROGRAM
%token S_PONTOVG
%token S_IDENTIF
%token S_ABREPAR
%token S_VIRGULA
%token S_ATRIBUI
%token S_NUMERO
%token S_WHILE
%token S_WRITE
%token S_READ
%token S_MAIOR
%token S_MENOR
%token S_IGUAL
%token S_MENOS
%token S_VEZES
%token S_PONTO
%token S_BEGIN
%token S_THEN
%token S_ELSE
%token S_MAIS
%token S_VAR
%token S_END
%token S_DIV
%token S_AND
%token S_NOT
%token S_IF
%token S_DO
%token S_OR
%%

/*+-----------------------------------------------------------+
  |        Producoes Sintaticas da Linguagem Pascal           |
  +-----------------------------------------------------------+*/
  
programa
	: S_PROGRAM S_IDENTIF { abreArquivoEscrita(atomo); } S_PONTOVG
		{ printf("\tINPP\n"); }
	bloco S_PONTO {
                if((TOPO_PSEMA != 0 ) && (PSEMA [TOPO_PSEMA-1].info.nivel_corrente == NIVEL_CORR)){
                    desempilha(c_nivelcor, &elem_pilha);
                    desempilha(c_nvloc, &elem_pilha);
                    elimina_simbolos(elem_pilha.info.nro_vlocais + NRO_ROTINAS);
                    /*strcat(saida, "\tDMEM\t");
                    itoa(elem_pilha.info.nro_vlocais, &aux3, 10);
                    strcat(saida, aux3);
                    printf("saida: %s", saida);
                    fwrite (saida, sizeof (char*), 1, arq_mep);
                    fclose(arq_mep);*/
                    printf("\tDMEM\t%d\n", elem_pilha.info.nro_vlocais);
                }
               	printf("\tPARA\n\tFIM\n\n");
        }
;

bloco
	: variaveis declaracao_de_funcoes_e_procendimentos corpo
;

variaveis
	: /* vazio */
	| S_VAR  { CONTA_VARS = 0; }
	declaracoes_de_vars {
            if(CONTA_VARS > 0){
                printf("\tAMEM\t%d\n", CONTA_VARS);
                empilha_num_val();
                empilha_nivel_corrente();
            }
	}
;

declaracoes_de_vars
	: declaracoes_de_vars lista_de_identificadores S_DOISPTOS S_IDENTIF S_PONTOVG
	| lista_de_identificadores S_DOISPTOS S_IDENTIF S_PONTOVG
;

declaracao_de_funcoes_e_procendimentos
	: /* vazio */
        | {
            printf ("\tDSVS\tL%d\n", ++ROTULO);
            empilha_rotulo(); 
          }
	  lista_de_declaracao_de_funcao_ou_procedimento S_PONTOVG
          {
            desempilha(c_rotul, &elem_pilha);
            NRO_ROTINAS++;
            printf ("L%d:\tNADA\n", elem_pilha.info.rotulo);
	  }
;

lista_de_declaracao_de_funcao_ou_procedimento
	:lista_de_declaracao_de_funcao_ou_procedimento 
		S_PONTOVG 
			{ 
				NIVEL_CORR++; 
			} 
	declaracao_de_funcao_ou_procedimento 
			{ 
				NIVEL_CORR--; 
			}
	| 		{ 
				NIVEL_CORR++; 
			} 
	declaracao_de_funcao_ou_procedimento 
            { 
				NIVEL_CORR--; 
            }
;

declaracao_de_funcao_ou_procedimento
	: 
			{ 
				ROTULO++; 
				NRO_ENTRADAS = 0; 
			}	 
	declaracao_de_procedimento 
			{ 
				elimina_simbolos(NRO_ENTRADAS);
			} 
	| 		{ 
				ROTULO++; 
				NRO_ENTRADAS = 0; 
			}	 
	declaracao_de_funcao
;

declaracao_de_procedimento 
        : 	{ 
				printf ("L%d:\tENPR \t%d\n", ROTULO, NIVEL_CORR); 
			}
		cabecalho_procedimento S_PONTOVG 
        bloco 
			{ 
				if((PSEMA [TOPO_PSEMA-1].cat == c_nivelcor) && (PSEMA [TOPO_PSEMA-1].info.nivel_corrente == NIVEL_CORR)){
					desempilha(c_nivelcor, &elem_pilha); 
					desempilha(c_nvloc, &elem_pilha);
					elimina_simbolos(elem_pilha.info.nro_vlocais); 
					printf("\tDMEM\t%d\n", elem_pilha.info.nro_vlocais);
				}
				printf ("\tRTPR \t%d,%d\n", NIVEL_CORR, NRO_ENTRADAS); 
			}
;

cabecalho_procedimento
        : S_PROCEDURE S_IDENTIF 
			{ 
				strcpy(NOME_PROC, atomo); 
				insere_procedimento(atomo); 
			}
        | S_PROCEDURE S_IDENTIF 
			{  
				PROX_PARAM = NULL;
				strcpy(NOME_PROC, atomo);
				insere_procedimento(atomo);
				marca_inicio_lista(ROTULO);
			}
		lista_formal_de_parametros
			{
				POS_PARAM = 1;
				aux = TOPO_PSEMA - 1;
				while(PSEMA [aux].cat != c_marca){
					aux--;
				}
				aux1 = aux++;
				cont = 0; 
				for(; aux < TOPO_PSEMA; aux++){
					if(PSEMA [aux].cat == c_ident){
						insere_parametro (PSEMA [aux].info.id, (-3 + (POS_PARAM - NRO_ENTRADAS)));
						POS_PARAM++;      
						cont++;
					}else{
						for(; cont > 0 ; cont--){
							insere_listapar(&PROX_PARAM, PSEMA [aux].info.mec);
						}
						cont = 0;  
					}          
				}
				TOPO_PSEMA = aux1;
				POS_SIMB = busca_simbolo (NOME_PROC);
				if (POS_SIMB == -1)
					msg_erro ("Procedimento [%s] nao declarado!", NOME_PROC);
				else {
					TSIMB[POS_SIMB].info.proc.pars = PROX_PARAM;
				}               
                  //escreve_lista_param (&TSIMB[POS_SIMB].info.proc.pars);
			}
;

lista_formal_de_parametros
	: S_ABREPAR secao_lista_de_parametros_formais S_FECHAPAR
;

secao_lista_de_parametros_formais
	: secao_lista_de_parametros_formais S_PONTOVG secao_de_parametros_formais  
	| secao_de_parametros_formais
;

secao_de_parametros_formais
	: parametro_por_valor
	| parametro_por_referencia
;

parametro_por_valor
	: lista_de_parametros 
		{
			empilha_mecanismo(pas_val);
		}
	S_DOISPTOS S_IDENTIF
;

parametro_por_referencia
	: S_VAR lista_de_parametros
		{
			empilha_mecanismo(pas_end);
		}
	S_DOISPTOS S_IDENTIF
;

declaracao_de_funcao
        : //S_FUNCTION S_IDENTIF S_PONTOVG bloco
			{ 
				printf ("L%d:\tENPR \t%d\n", ROTULO, NIVEL_CORR); 
			} 
		cabecalho_funcao S_PONTOVG 
        bloco
            { 
                if((PSEMA [TOPO_PSEMA-1].cat == c_nivelcor) && (PSEMA [TOPO_PSEMA-1].info.nivel_corrente == NIVEL_CORR)){
                    desempilha(c_nivelcor, &elem_pilha); 
                    desempilha(c_nvloc, &elem_pilha);
					elimina_simbolos(elem_pilha.info.nro_vlocais); 
                    printf("\tDMEM\t%d\n", elem_pilha.info.nro_vlocais);
                }
                printf ("\tRTPR \t%d,%d\n", NIVEL_CORR, NRO_ENTRADAS);
            }
;

cabecalho_funcao
        : S_FUNCTION S_IDENTIF S_DOISPTOS 
			{ 
				strcpy(NOME_PROC, atomo); 
				insere_funcao(atomo); 
			}
		tipo_de_retorno
        | S_FUNCTION S_IDENTIF 
			{  
				PROX_PARAM = NULL;
				strcpy(NOME_PROC, atomo);
				insere_funcao(atomo);
				marca_inicio_lista(ROTULO);
			}
		lista_formal_de_parametros
			{
				POS_PARAM = 1;
				aux = TOPO_PSEMA - 1;
				while(PSEMA [aux].cat != c_marca){
					aux--;
				}
				aux1 = aux++;
				cont = 0; 
				for(; aux < TOPO_PSEMA; aux++){
					if(PSEMA [aux].cat == c_ident){
						insere_parametro (PSEMA [aux].info.id, (-3 + (POS_PARAM - NRO_ENTRADAS)));
						POS_PARAM++;      
						cont++;
					}else{
						for(; cont > 0 ; cont--){
							insere_listapar(&PROX_PARAM, PSEMA [aux].info.mec);
						}
						cont = 0;  
					}          
				}             
				TOPO_PSEMA = aux1;
				POS_SIMB = busca_simbolo (NOME_PROC);
				if (POS_SIMB == -1)
					msg_erro ("Funcao [%s] nao declarada!", NOME_PROC);
				else {
					TSIMB[POS_SIMB].info.func.desloca = -3 - (POS_PARAM - 1);
					TSIMB[POS_SIMB].info.proc.pars = PROX_PARAM;
				}
                  
                  //escreve_lista_param (&TSIMB[POS_SIMB].info.func.pars);
			}
		S_DOISPTOS tipo_de_retorno
;

tipo_de_retorno:
	S_IDENTIF
;

chamada_funcao: 
        S_IDENTIF 
            {
                POS_SIMB = busca_nome_proc(atomo);
                if (POS_SIMB == -1)
		    msg_erro ("Funcao [%s] nao declarada!", atomo);
	        else {        
                    empilha_identificador();
                    PRE_ROTINA = 1;
                    cont = 0;
                    printf ("\tAMEM \t1\n");  
                } 
            }       
        parametros
			{
				desempilha(c_ident, &elem_pilha);
				POS_SIMB = busca_nome_proc (elem_pilha.info.id);
				if (POS_SIMB == -1)
					msg_erro ("Funcao [%s] nao declarada!", atomo);
				else {
					printf ("\tCHPR \tL%d\n", TSIMB[POS_SIMB].info.func.rot_int); 
				}
				PRE_ROTINA = 0;                   
			} 
;

lista_de_parametros
	: lista_de_parametros S_VIRGULA S_IDENTIF
		{ 
			empilha_identificador(); NRO_ENTRADAS++;
		}
	| S_IDENTIF
		{ 
			empilha_identificador(); NRO_ENTRADAS++; 
		}
;

lista_de_identificadores
	: lista_de_identificadores S_VIRGULA S_IDENTIF
		{ 
			insere_variavel (atomo); CONTA_VARS++; 
		}
	| S_IDENTIF
		{ 
			insere_variavel (atomo); CONTA_VARS++; 
		}
;

corpo
	: S_BEGIN sequencia_comandos S_END
;

sequencia_comandos
	: sequencia_comandos S_PONTOVG comando_sem_rotulo
	| comando_sem_rotulo
;

comando_sem_rotulo
	: /* vazio */
	| chamada_procedimento
	| chamada_comando_escrita
	| chamada_comando_leitura
	| atribuicao
	| composto
	| condicional
	| repetitivo
;

chamada_procedimento
	: S_IDENTIF
		{
			POS_SIMB = busca_nome_proc (atomo);
			if (POS_SIMB == -1)
				msg_erro ("Procedimento [%s] nao declarado!", atomo);
			else {        
				empilha_identificador();
				PRE_ROTINA = 1;
				cont = 0;
			}
		}  
	parametros
		{
			desempilha(c_ident, &elem_pilha);
			POS_SIMB = busca_nome_proc (elem_pilha.info.id);
			if (POS_SIMB == -1)
				msg_erro ("Procedimento [%s] nao declarado!", atomo);
			else {
				printf ("\tCHPR \tL%d\n", TSIMB[POS_SIMB].info.proc.rot_int); 
			}
			PRE_ROTINA = 0;
		}
	| S_IDENTIF 
		{
			POS_SIMB = busca_nome_proc (atomo);
			if (POS_SIMB == -1)
				msg_erro ("Procedimento [%s] nao declarado!", atomo);
			else {
				empilha_identificador();
			}
		}   
;

parametros
	: S_ABREPAR lista_de_parametros_atuais S_FECHAPAR
;

lista_de_parametros_atuais
	: lista_de_parametros_atuais S_VIRGULA parametros_atuais
	| parametros_atuais
;

parametros_atuais
	: expressao
	| expressao S_DOISPTOS expressao
	| expressao S_DOISPTOS expressao S_DOISPTOS expressao
;

chamada_comando_escrita
	: S_WRITE S_ABREPAR lista_de_expressoes S_FECHAPAR

;

chamada_comando_leitura
	: S_READ S_ABREPAR lista_de_variaveis S_FECHAPAR
;

lista_de_expressoes
	: /* vazio */
	| lista_de_expressoes S_VIRGULA expressao
		{ printf("\tIMPR\n"); }
	| expressao
		{ printf("\tIMPR\n"); }
;

lista_de_variaveis
	: /* vazio */
	| lista_de_variaveis S_VIRGULA S_IDENTIF
		{
			POS_SIMB = busca_simbolo (atomo);
			if (POS_SIMB == -1)
				msg_erro ("Variavel [%s] nao declarada!", atomo);
			else {
				if(TSIMB [POS_SIMB].cat == cat_var){ 
					printf ("\tLEIT\n"); 
					printf ("\tARMZ\t%d,%d\n", TSIMB [POS_SIMB].nivel, TSIMB [POS_SIMB].info.var.desloca);
				}else{
					if(TSIMB [POS_SIMB].info.par.mec == pas_val){
						printf ("\tLEIT\n"); 
						printf ("\tARMZ\t%d,%d\n", TSIMB [POS_SIMB].nivel, TSIMB [POS_SIMB].info.par.desloca);
					}else{
						printf ("\tLEIT\n");
						printf ("\tARMI\t%d,%d\n", TSIMB [POS_SIMB].nivel, TSIMB [POS_SIMB].info.par.desloca);
					}
				}
			}
		}
	| S_IDENTIF
		{
		    POS_SIMB = busca_simbolo (atomo);
		    if (POS_SIMB == -1)
		        msg_erro ("Variavel [%s] nao declarada!", atomo);
		    else {
				if(TSIMB [POS_SIMB].cat == cat_var){ 
					printf ("\tLEIT\n"); 
					printf ("\tARMZ\t%d,%d\n", TSIMB [POS_SIMB].nivel, TSIMB [POS_SIMB].info.var.desloca);
				}else{
					if(TSIMB [POS_SIMB].info.par.mec == pas_val){
						printf ("\tLEIT\n"); 
						printf ("\tARMZ\t%d,%d\n", TSIMB [POS_SIMB].nivel, TSIMB [POS_SIMB].info.par.desloca);
					}else{
						printf ("\tLEIT\n");
						printf ("\tARMI\t%d,%d\n", TSIMB [POS_SIMB].nivel, TSIMB [POS_SIMB].info.par.desloca);
					}
				}
			}
		}

;

atribuicao
	: S_IDENTIF
		{
			POS_SIMB = busca_simbolo (atomo);
		    if (POS_SIMB == -1){
				msg_erro ("Variavel [%s] nao declarada!", atomo); 
		    }
		    else {
				empilha_identificador();
			}
		}
	S_ATRIBUI expressao
		{   
			desempilha(c_ident, &elem_pilha);
		    POS_SIMB = busca_simbolo (elem_pilha.info.id);
		    if (POS_SIMB == -1){
				msg_erro ("Variavel [%s] nao declarada!", elem_pilha.info.id);	
			}else {
				if(TSIMB [POS_SIMB].cat == cat_fun){
					printf ("\tARMZ\t%d,%d\n", TSIMB [POS_SIMB].nivel, TSIMB [POS_SIMB].info.func.desloca);
				}else{
					if(TSIMB [POS_SIMB].cat == cat_var){ 
						printf ("\tARMZ\t%d,%d\n", TSIMB [POS_SIMB].nivel, TSIMB [POS_SIMB].info.var.desloca);
					}else{
						if(TSIMB [POS_SIMB].info.par.mec == pas_val)
							printf ("\tARMZ\t%d,%d\n", TSIMB [POS_SIMB].nivel, TSIMB [POS_SIMB].info.par.desloca);
						else
							printf ("\tARMI\t%d,%d\n", TSIMB [POS_SIMB].nivel, TSIMB [POS_SIMB].info.par.desloca);
					}
				}
			}	
		}
;

composto
	: S_BEGIN lista_comandos_sem_rotulo S_END
;


lista_comandos_sem_rotulo
	: lista_comandos_sem_rotulo S_PONTOVG comando_sem_rotulo
	| comando_sem_rotulo
;

condicional
	: S_IF expressao
		{
			printf ("\tDSVF\tL%d\n", ++ROTULO);
			empilha_rotulo();
		}
	S_THEN comando_sem_rotulo alternativa_cond
		{
			desempilha(c_rotul, &elem_pilha);
			printf ("L%d:\tNADA\n", elem_pilha.info.rotulo);
		}
;

alternativa_cond
	: /* vazio */
	| S_ELSE
		{
			printf ("\tDSVS\tL%d\n", ++ROTULO);
			desempilha(c_rotul, &elem_pilha);
			printf ("L%d:\tNADA\n", elem_pilha.info.rotulo);
			empilha_rotulo();
		}
	comando_sem_rotulo
;

repetitivo
	: S_WHILE
		{
			printf ("L%d:\tNADA\n", ++ROTULO);
			empilha_rotulo();
		}
	expressao
		{
			printf ("\tDSVF\tL%d\n", ++ROTULO);
			empilha_rotulo();
		}
	S_DO comando_sem_rotulo
		{
			desempilha(c_rotul, &elem_pilha);
			aux = elem_pilha.info.rotulo;
			desempilha(c_rotul, &elem_pilha);
			printf ("\tDSVS\tL%d\n", elem_pilha.info.rotulo);
			printf ("L%d:\tNADA\n", aux);
		}
;

expressao
        : expressao_simples

	| expressao_simples S_IGUAL expressao_simples
		{ printf ("\tCMIG\n"); }
	| expressao_simples S_DIFERENTE expressao_simples
		{ printf ("\tCMDG\n"); }
	| expressao_simples S_MENOR expressao_simples
		{ printf ("\tCMME\n"); }
	| expressao_simples S_MAIOR expressao_simples
		{ printf ("\tCMMA\n"); }
	| expressao_simples S_MEIGUAL expressao_simples
		{ printf ("\tCMEG\n"); }
	| expressao_simples S_MAIGUAL expressao_simples
		{ printf ("\tCMAG\n"); }
;

expressao_simples
        : termo

	| expressao_simples { PRE_ROTINA = 0; } S_MAIS termo
		{ printf ("\tSOMA\n"); }
	| expressao_simples { PRE_ROTINA = 0; } S_MENOS termo
		{ printf ("\tSUBT\n"); }
	| expressao_simples S_OR termo
		{ printf ("\tDISJ\n"); }
	| S_MAIS termo
	| S_MENOS termo
		{ printf ("\tINVR\n"); }
;

termo
        : fator
 
	| termo { PRE_ROTINA = 0; } S_VEZES fator
		{ printf ("\tMULT\n"); }
	| termo { PRE_ROTINA = 0; } S_DIV fator
		{ printf ("\tDIVI\n"); }
	| termo S_AND fator
		{ printf ("\tCONJ\n"); }
;

fator
	: S_IDENTIF
		{
		    POS_SIMB = busca_simbolo (atomo);
		    if (POS_SIMB == -1)
		        msg_erro ("Variavel [%s] nao declarada!", atomo);
		    else{
				if(TSIMB [POS_SIMB].cat == cat_var){
					if(PRE_ROTINA){
						POS_SIMB = busca_nome_proc(PSEMA[TOPO_PSEMA-1].info.id);
						aux = retorna_mec(cont++, &TSIMB [POS_SIMB].info.proc.pars);
						POS_SIMB = busca_simbolo (atomo);
						if(aux == 0){ 
							printf ("\tCREN\t%d,%d\n", TSIMB [POS_SIMB].nivel, TSIMB [POS_SIMB].info.var.desloca);
						}else{
							printf ("\tCRVL\t%d,%d\n", TSIMB [POS_SIMB].nivel, TSIMB [POS_SIMB].info.var.desloca);
						}
					}else{
						printf ("\tCRVL\t%d,%d\n", TSIMB [POS_SIMB].nivel, TSIMB [POS_SIMB].info.var.desloca);   
					}
				}else{
					printf ("\tCRVL\t%d,%d\n", TSIMB [POS_SIMB].nivel, TSIMB [POS_SIMB].info.par.desloca);
				}
			} 
		}			
	| S_NUMERO
		{ printf ("\tCRCT\t%s\n", atomo); }
        | chamada_funcao
	| S_ABREPAR expressao S_FECHAPAR
	| S_NOT fator
		{ printf ("\tNEGA\n"); }
;

%%

yywrap () {}

yyerror (char *s) {
	printf ("\n\nERRO SINTATICO na linha: %d\n\n",numero_da_linha);
}

main (void) {

	yyparse ();
}
