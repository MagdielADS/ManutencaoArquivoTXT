/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.upper.manutencaosped.teste;

/**
 *
 * @author Magdiel Ildefonso
 */
public class teste {

    public static void main(String[] args) {
        String caminho = "dados/log.txt";
        String[] aux = caminho.split("/");
        String arquivoTmp = "";
        for (int i = 0; i < aux.length-1; i++) {
            arquivoTmp += aux[i];
        }

        System.out.println(arquivoTmp);
    }
}
