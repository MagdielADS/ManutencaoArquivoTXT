/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.upper.manutencaosped.excecoes;

/**
 *
 * @author Magdiel Ildefonso
 */
public class OutColumnOfFileLimits extends Exception {

    /**
     * Creates a new instance of <code>OutColumnOfFileLimits</code> without
     * detail message.
     */
    public OutColumnOfFileLimits() {
    }

    /**
     * Constructs an instance of <code>OutColumnOfFileLimits</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public OutColumnOfFileLimits(String msg) {
        super(msg);
    }
}
