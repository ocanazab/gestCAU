package com.nacho.gestCAU.util;

public class Cifrado {

    String mayus = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789";
    String minus = "abcdefghijklmnñopqrstuvwxyz0123456789";
    char [] texto;
    char[] mayusculas;
    char[] minusculas;

    public Cifrado(){

    }

    public String cifra (String cadena, int clave){

        //Para cifrar, cada caracter es sustituido por su siguiente en el abecedario indicado por la clave.
        //Por ejemplo: A con clave de 1 se convierte en B


        //Declaración de variables. Transformo todas las cadenas en Arrays de caracteres.
        texto=cadena.toCharArray();
        mayusculas=mayus.toCharArray();
        minusculas=minus.toCharArray();
        char[] textoCifrado = new char[texto.length];


        //Recorro el texto a cifrar caracter a caracter.
        for (int i=0;i<=texto.length-1;i++){
            //Localizo el caracter en Mayusculas
            int j=0;
            while(j<mayusculas.length){
                if (texto[i]==mayusculas[j]){
                    //Si el caracter es una Z, la cambio por una A.
                    if (mayusculas[j]=='Z'){
                        textoCifrado[i]='A';
                    }else{
                        textoCifrado[i]=mayusculas[j+clave];
                    }
                }
                j++;
            }
            //Localizo el caracter en Minusculas
            int k=0;
            while(k<minusculas.length){
                if (texto[i]==minusculas[k]){
                    //Si el caracter es una z, la cambio por una a.
                    if (minusculas[k]=='z'){
                        textoCifrado[i]='a';
                    }else{
                        textoCifrado[i]=minusculas[k+clave];
                    }
                }
                k++;
            }
        }
        //Transformo el Array de caracteres en un String.
        String cifrada = new String(textoCifrado);
        return cifrada;
    }

    public String descifra (String cadena, int clave){

        //Para descifrar, deshacemos el movimiento de caracteres realizado al cifrar.
        //Es necesario usar la misma clave que la usada en el Cifrado.

        //Declaración de variables. Al igual que antes, transformo todas las cadenas en Arrays de caracteres.

        texto=cadena.toCharArray();
        mayusculas=mayus.toCharArray();
        minusculas=minus.toCharArray();
        char[] textoDescifrado = new char[texto.length];

        //Recorro la cadena a descifrar.
        for (int i=0;i<=texto.length-1;i++){
            //Localizo el caracter en Mayusculas
            int j=0;
            while(j<mayusculas.length){
                if (texto[i]==mayusculas[j]){
                    //Si el caracter es una A, la cambio por una Z.
                    if (mayusculas[j]=='A'){
                        textoDescifrado[i]='Z';
                    }else{
                        textoDescifrado[i]=mayusculas[j-clave];
                    }
                }
                j++;
            }
            //Localizo el caracter en Minusculas
            int k=0;
            while(k<minusculas.length){
                if (texto[i]==minusculas[k]){
                    //Si el caracter es una a, la cambio por una z.
                    if (minusculas[k]=='a'){
                        textoDescifrado[i]='z';
                    }else{
                        textoDescifrado[i]=minusculas[k-clave];
                    }
                }
                k++;
            }
        }
        //Transformo el Array de caracteres en un String.
        String descifrada = new String(textoDescifrado);
        return descifrada;
    }
}
