package com.ouahidi.ragtext.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    public Controller(ChatClient.Builder  bulider, VectorStore vectorStore) {
        this.chatClient = bulider
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()))
                .build();
    }

    ChatClient chatClient;

@GetMapping(value = "/iam/{userprompt}", produces=MediaType.TEXT_MARKDOWN_VALUE)
    public String imainfos(@PathVariable  String userprompt) {
        return chatClient
                .prompt()
                .user(userprompt)
                .call()
                .content();
    }


    }

