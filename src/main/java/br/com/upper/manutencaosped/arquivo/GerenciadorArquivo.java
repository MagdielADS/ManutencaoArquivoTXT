/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.upper.manutencaosped.arquivo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Magdiel Ildefonso
 */
public class GerenciadorArquivo {

    public static int alterarLinhasPorRegistro(String caminho, String registro, int coluna, String valorAntigo, String valorNovo) {
        List<String> linhas = new ArrayList<String>();
        FileReader arq;
        int qtde = 0;
        try {

            String aux = caminho.replace("\\", "#");
            String arquivoTmp = "";

            String[] vetor = aux.split("#");
            for (int i = 0; i < vetor.length - 1; i++) {
                arquivoTmp += vetor[i] + "\\";
            }
            arquivoTmp += vetor[vetor.length-1].substring(0, vetor[vetor.length-1].indexOf("."));
            
            arquivoTmp += "-modificado.txt";
            System.out.println(arquivoTmp);
            arq = new FileReader(caminho);
            BufferedReader lerArq = new BufferedReader(arq);
            BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoTmp));

            String linha = lerArq.readLine();
            

            while (linha != null) {
                linha = linha.replace("|", "´");
                String[] valores = linha.split("´");

                if (valores[1].equals(registro)) {
                    if (valores[coluna + 1].equals(valorAntigo)) {
                        valores[coluna + 1] = valorNovo;
                        qtde++;
                    }

                    String novaLinha = "";
                    for (String string : valores) {
                        novaLinha += "|"+string;
                    }
                    novaLinha = novaLinha.substring(1);
                    novaLinha += "|";
                    System.out.println(novaLinha);
                    novaLinha += System.getProperty("line.separator");
                    writer.write("--"+novaLinha);
                }else{
                    linha = linha.replace("´", "|");
                    System.out.println(linha);
                    linha += System.getProperty("line.separator");
                    writer.write(linha);
                }

                linha = lerArq.readLine();
            }

            arq.close();
            lerArq.close();
            writer.close();

            //new File(caminho).delete();
            //new File(arquivoTmp).renameTo(new File(caminho));
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GerenciadorArquivo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GerenciadorArquivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return qtde;
    }

}
