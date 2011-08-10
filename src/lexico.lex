/*+--------------------------------------------------------------------+
  |   Projeto - COMPILADOR PASCAL.                                     |
  |   Por: Luiz Eduardo da Silva (luizedu@unis.edu.br).                |
  |        Everton Josué da Silva (evertonjsilva31@gmail.com)          |
  +--------------------------------------------------------------------+*/

%%

%class lexico
%line
%column
%cup
%ignorecase

%{

    private Simbolo symbol(int type) {
        return new Simbolo(type, yyline+1, yycolumn+1);
    }
    private Simbolo symbol(int type, Object value) {
        return new Simbolo(type, yyline+1, yycolumn+1, value);
    }
%}


FimDeLinha  = \r|\n|\r\n
Espaco      = {FimDeLinha} | [ \t\f]
Comentario  = {Tradicional} | {Parenteses}
Tradicional = "{" ~"}"
Parenteses  = "(*" ~"*)"
Numero      = 0 | [1-9][0-9]*
Identif     = [A-Za-z_][A-Za-z_0-9]*

%%

<YYINITIAL> {
   function       { return symbol (sym.S_FUNCTION); }  
   procedure      { return symbol (sym.S_PROCEDURE); }
   if             { return symbol (sym.S_IF); }
   then           { return symbol (sym.S_THEN); }
   else           { return symbol (sym.S_ELSE); }
   while          { return symbol (sym.S_WHILE); }
   do             { return symbol (sym.S_DO); }
   program        { return symbol (sym.S_PROGRAM); }
   var            { return symbol (sym.S_VAR); }
   begin          { return symbol (sym.S_BEGIN); }
   end            { return symbol (sym.S_END); }
   div            { return symbol (sym.S_DIV); }
   and            { return symbol (sym.S_AND); }
   or             { return symbol (sym.S_OR); }
   not            { return symbol (sym.S_NOT); }
   write          { return symbol (sym.S_WRITE); }
   read           { return symbol (sym.S_READ); }
   repeat         { return symbol (sym.S_REPEAT); }
   until          { return symbol (sym.S_UNTIL); }
   "+"            { return symbol (sym.S_MAIS); }
   "-"            { return symbol (sym.S_MENOS); }
   "*"            { return symbol (sym.S_VEZES); }
   ":="           { return symbol (sym.S_ATRIBUI); }
   ":"            { return symbol (sym.S_DOISPTOS); }
   "."            { return symbol (sym.S_PONTO); }
   ";"            { return symbol (sym.S_PONTOVG); }
   ","            { return symbol (sym.S_VIRGULA); }
   "("            { return symbol (sym.S_ABREPAR); }
   ")"            { return symbol (sym.S_FECHAPAR); }
   ">"            { return symbol (sym.S_MAIOR); }
   "<"            { return symbol (sym.S_MENOR); }
   ">="           { return symbol (sym.S_MAIGUAL); }
   "<="           { return symbol (sym.S_MEIGUAL); }
   "="            { return symbol (sym.S_IGUAL); }
   "<>"           { return symbol (sym.S_DIFERENTE); }
   {Numero}       { return symbol (sym.S_NUMERO, yytext());}
   {Identif}      { return symbol (sym.S_IDENTIF, yytext());}
   {Espaco}       { /* Pula sem fazer nada */ }
   {Comentario}   { /* Pula sem fazer nada */ }
}

[^]               { System.out.println ("Simbolo Desconhecido:  <"+yytext()+">");}
