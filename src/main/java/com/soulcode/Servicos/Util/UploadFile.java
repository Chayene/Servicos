package com.soulcode.Servicos.Util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
// essa clase faz upload do arquivos pdf, jpg
//metodo class UploadFil, uploadDir (caminho do arquivo, link), MultipartFile file(arquivo em si, jpg..)
public class UploadFile {
    public static void saveFile(String uploadDir, String fileName, MultipartFile file) throws IOException {
        Path uploadPath= Paths.get(uploadDir); //instaciamos o objeto = fazemos a leitura e add no objeto e verificamos se existe
        if(!Files.exists(uploadPath)){ // se o caminho não existe,
            Files.createDirectories(uploadPath); //  criar um diretorio e o sistema lança uma execeção throws.IOException
        }

        // aqui vamos tentar fazer o upload do arquivo
        // InputStream-  tenta fazer a leitura do arquivo que estamos querendo subir
        // faz a leitura bit por bit do arquivo
        try(InputStream inputStream=file.getInputStream()){ //upload fluxo de dados faz a leitura bit a bit= get pega o arquivo

            // nesse momento o arquivo é salvo no diretorio passado na assinatura  do método e no resolve coloca o nome do arquivo
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING); //copia do tipo replace_existing para substituir // faz uma copia no servidor ou no local q quer armazenar
        }

        catch (IOException e){
            throw new IOException("não foi possivel enviar o seu arquivo");
        }
    }

}
