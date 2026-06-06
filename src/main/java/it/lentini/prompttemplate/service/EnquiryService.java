package it.lentini.prompttemplate.service;

import it.lentini.prompttemplate.dto.TripEnquiry;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.lang.IO.println;

@Service
public class EnquiryService {

    private ChatClient chatClient;
    private String enquiryTemplate = """
                        You are an assistant for Tripoffering, a website helping customers finding tour operators to sort out their 
                        trip needs in various destinations around the world. 
            
                        Customer details:
                        - Name: {customerName}
            
                        Enquiry message:
                        \\"\\"\\"
                        {enquiryMessage}
                        \\"\\"\\"
            
                        Currently the only destinations that are being offered are Vietnam and Sri Lanka.
            
                        Your task is find what the customer is looking for their trip and suggest a destination that best fits the 
                        taste of the customer. If there is enough information to suggest a destination you will want to reply 
                        to the customer suggesting said destination briefly talking about why the customer would want to go there,
                        or otherwise ask the customer another question to narrow down the options.
            
                        Return the result in the following JSON format only:
            \\{
                "reply": "The content of the reply to send back to the customer",
                "destination": "If a destination has been found this will contain the name of the destination, otherwise \\"N/A\\",
                "reply_type": "SUGGESTION if a destination has been found and suggested to the user, QUESTION otherwise"
            \\}
            """;
    PromptTemplate promptTemplate = new PromptTemplate(enquiryTemplate);

    public EnquiryService(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    public String processEnquiry(TripEnquiry tripEnquiry) {

        Prompt prompt = promptTemplate.create(Map.of(
                "customerName", tripEnquiry.name(),
                "enquiryMessage", tripEnquiry.question()));

        String response = chatClient.prompt(prompt)
                .call()
                .content();

        println(response);

        return response;
    }
}
