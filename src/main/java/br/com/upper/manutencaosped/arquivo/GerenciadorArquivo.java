/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.upper.manutencaosped.arquivo;

import br.com.upper.manutencaosped.excecoes.OutColumnOfFileLimits;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Magdiel Ildefonso
 */
public class GerenciadorArquivo {

    public static int alterarLinhasPorRegistro(String caminho, String destino, String registro, int coluna, String valorAntigo, String valorNovo) throws FileNotFoundException, IOException, OutColumnOfFileLimits {
        List<String> linhas = new ArrayList<String>();
        FileReader arq;
        int qtde = 0;
        String arquivoTmp = "";
        if (destino.equals("") || destino.equals("")) {
            Calendar data = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss");
            String dataFormat = df.format(data.getTime());
            arquivoTmp += caminho.substring(0, caminho.indexOf("."));
            arquivoTmp += "-" + dataFormat + "-modif.txt";
        } else {
            Calendar data = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss");
            String dataFormat = df.format(data.getTime());

            String aux = caminho.replace('\\', '/');
            String[] vetorAux = aux.split("/");
            arquivoTmp += destino + "\\";
            arquivoTmp += vetorAux[vetorAux.length - 1].substring(0, vetorAux[vetorAux.length - 1].indexOf("."));

            arquivoTmp += "-" + dataFormat + "-modif.txt";
        }

        arq = new FileReader(caminho);
        BufferedReader lerArq = new BufferedReader(arq);
        BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoTmp));

        String linha = lerArq.readLine();

        if (linha.charAt(0) == '|') {
            while (linha != null) {
                String[] valores = linha.split("\\|");

                if (coluna > valores.length - 1) {
                    throw new OutColumnOfFileLimits("Coluna " + String.valueOf(coluna) + " não existe no registro " + registro);
                }

                if (valores[1].equalsIgnoreCase(registro)) {
                    if (valores[coluna + 1].equals(valorAntigo)) {
                        valores[coluna + 1] = valorNovo;
                        qtde++;
                    } else if (valorAntigo.equals("") || valorAntigo.equals(" ")) {
                        valores[coluna + 1] = valorNovo;
                        qtde++;
                    }

                    String novaLinha = "";
                    for (String string : valores) {
                        novaLinha += "|" + string;
                    }
                    novaLinha = novaLinha.substring(1);
                    novaLinha += "|";
                    System.out.println(novaLinha);
                    novaLinha += System.getProperty("line.separator");
                    writer.write(novaLinha);
                } else {
                    System.out.println(linha);
                    linha += System.getProperty("line.separator");
                    writer.write(linha);
                }

                linha = lerArq.readLine();
            }

            if (qtde <= 0) {
                System.out.println("Não achei o arquivo " + arquivoTmp);
                System.out.println(new File(arquivoTmp).delete());
            }
            arq.close();
            lerArq.close();
            writer.close();

            if (qtde <= 0) {
                new File(arquivoTmp).delete();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Formato de arquivo inválido", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return qtde;
    }

}
