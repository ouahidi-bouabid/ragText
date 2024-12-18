package com.ouahidi.ragtext.config;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.List;

@Configuration
public class Config {
 @Value("classpath:/iam.txt")
 private Resource infoIam;
@Bean
 SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel)
 {
     SimpleVectorStore vectorStore = new SimpleVectorStore(embeddingModel);
     File vectorStoreFile = new File("C:\\Users\\bouab.SPECTRE\\Desktop\\IA-GENERTAIVE\\TPs\\RAG\\ragText\\src\\main\\resources\\vectorStor.json");

if (vectorStoreFile.exists()){
    System.out.println("Chargé le fichier vectorStoreFile");
    vectorStore.load(vectorStoreFile);
} else {
    System.out.println("Creation du vectorStorFile");
    // On va lire le document iam.txt à travers la variable iamInfo
    TextReader textReader = new TextReader(infoIam);
    // Configuraton de metadata
    textReader.getCustomMetadata().put("filename", "iam.txt");
    // la liste <Document>
    List<Document> documents = textReader.get();
    // On va textSpliter pour spliter en tokens
    TextSplitter textSplitter = new TokenTextSplitter();
// On split en token  la list<Document > documents
    List<Document> documentsTokenized = textSplitter.apply(documents);

    // le modèle embedding va être appliqué une fois ajouté au vectorStore
    vectorStore.add(documentsTokenized);

    vectorStore.save(vectorStoreFile);
}

return vectorStore;

}

}


