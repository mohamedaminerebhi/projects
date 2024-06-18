import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Parser {


    String[] LRGS = {
           "S->void main ( ) { P } ",
    "S->int main ( ) { P return E ; } ",
    "P->D ; I ; ",
    "D->D ; D ",
    "D->T E ",
    "D->T E array [ E ] ",
    "T->int ",
     "T->float ",
    "T->bool ",
    "T->long ",
    "T->string ",
    "I->I ; I ",
    "I->A ",
    "I->if ( E ) then { I } ",
    "I->if ( E ) then { I } else { I } ",
    "I->for ( A , E ) { I } ",
    "I->scanf ( E )",
    "I->printf ( E ) ",
    "A->E = E ",
    "E->l ",
    "E->nb ",
    "E->real ",
    "E->id ",
    "E->id [ E ] ",
    "E->E == E ",
    "E->E < E ",
    "E->E > E ",
    "E->E + E ",
    "E->E - E ",
    "E->E * E ",
    "E->E / E " ,
    "E->E ; E ",
    "E->E , E ",
    "E->E ++ ",
    "E->E -- ",
    "E->\"% F \" ",
    "E->& E ",
    "E->( E ) ",
    "F->d ",
    "F->f " ,
    "F->c ",
    "F->s "
    };


    public CSVReader table =new CSVReader();
    public String [][] tableSLR;
    public String [] header;





    public Stack<String> stackState = new Stack<>();


public Stack<String> analyse = new Stack<>();

public Stack<String> stackSymbol = new Stack<>();


public String strInput;

public String action = "";



int index = 0;




    public Parser (String []tt) {

    action = "";
    index = 0;
tableSLR=table.getData();
header =table.getHeaders();
analyse.push("0");


    System.out.println("********pile     	    Entrée            Action***********");
    this.AfficherSLRnew(tt);

    while(index<tt.length)

    {


        //  String s = stackState.peek();

        String s = analyse.peek();

       String act=Action(s,tt[index]);

        if (Action(s,tt[index]).charAt(0) == 's') {


            //stackState.push(Action(s, ch[index]).substring(1));
            //stackSymbol.push(ch[index]);

            analyse.push(tt[index]);
            analyse.push(Action(s, tt[index]).substring(1));




            index++;
            action = "shift ";

            AfficherSLRnew(tt);
        }
        // Réduction
        else if (Action(s,tt[index]).charAt(0) == 'r') {
            //
            String str = LRGS[Integer.parseInt(Action(s, tt[index]).substring(1)) - 1];

            int pos= str.indexOf('>');

            String tabparties[]= str.split("->");


            String Partiegauche=tabparties[0];
            // System.out.println("Partiegauche"+Partiegauche);

            String Partiedroite=tabparties[1];
           // System.out.println("Partiedroite"+Partiedroite);

            String tabtoken[]= Partiedroite.split(" ");
            int taillepile= tabtoken.length +tabtoken.length;


            for (int i = 0; i < taillepile; i++) {



                analyse.pop();

            }
            String sommetpile = analyse.peek();

            analyse.push(Partiegauche);
            String tetesucc = analyse.peek();


            analyse.push(Action(sommetpile, Partiegauche));


            action = "reduce:" + str;
            AfficherSLRnew(tt);
        }
        //acceptation
        else if (Action(s,tt[index]) .equals( "acc"))
        {
            System.out.println("analyze SLR successfully");
            break;}

        else
        //erreur
        {

            //System.out.println("texterreur"+Action(s,ch[index])+s+ch[index]+index);
            System.out.println("analyze SLR failled");
            break;
        }

    }

}




public String Action(String s, String a) throws NullPointerException{

    int entier = Integer.parseInt(s);

            for (int j = 1; j < header.length; j++)
                if (header[j].equals(a)) {



                    return tableSLR[entier][j];
                }
    return "err";

}







public void AfficherSLRnew(String []tt) {
    //  SLR


    String ss= "-------";
    String ss1= "-------";
    int taillepile=analyse.size();
    int taillepilediv2= taillepile /2;
    for(int i=0;i<taillepilediv2;i++)
        ss=ss + "-------" ;
    int tailleinput=tt.length;
    for(int i=0;i<tailleinput;i++)
        ss1=ss1 + "-------" ;





    strInput="";
    for(int i=index; i<tt.length;i++)
        strInput= strInput+ tt[i];

    System.out.printf("%s", analyse + ss1);
    System.out.printf("%s", strInput+ ss);
    System.out.printf("%s", action);
    System.out.println();
}

public void ouput() {


    System.out.println("**********Tableau SLR¨********");

    for (int i = 0; i < 109; i++) {
        for (int j = 0; j <54; j++) {
            System.out.printf("%6s", tableSLR[i][j] + " ");
        }
        System.out.println();
    }
    System.out.println("**********Fin tableau SLR********");

}










}
