package com.example.johndoe.demoqr;

/**
 * Created by John Doe on 6/26/2016.
 */
public class RegistroVideo {

    public String nomeArquivo;
    public String nomeApresentacao;

    /* Cria um novo registro de video.
     *
     * @args arq nome do arquivo
     * @args apr nome de apresentacao
     */
    public RegistroVideo(String arq, String apr){
        this.nomeArquivo = arq;
        this.nomeApresentacao = apr;
    }

}
