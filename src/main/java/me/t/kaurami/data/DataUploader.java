package me.t.kaurami.data;

import me.t.kaurami.entities.Agent;
import me.t.kaurami.entities.Client;
import me.t.kaurami.service.bookReader.BookReader;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class DataUploader {

    private BookReader reader;
    private AgentRepository agentRepository;
    private ClientRepository clientRepository;
    LinkedList<LinkedList<String>> sourceData;
    private Map<String, Integer> columnNumbers;
    private Map<String, Agent> agentMap;
    private Map<String, Client> clientMap;

    @Resource(name = "fields")
    private Map<String, Map<String, List<String>>> fields;
    private String nameOfSourceColumnMap = "CLIENTS_LIST";

    public DataUploader(BookReader reader, AgentRepository agentRepository, ClientRepository clientRepository) {
        this.reader = reader;
        this.agentRepository = agentRepository;
        this.clientRepository = clientRepository;
    }

    public void setReader(BookReader reader) {
        this.reader = reader;
    }

    public void setFields(Map<String, Map<String, List<String>>> fields) {
        this.fields = fields;
    }

    public void setAgentRepository(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void uploadData(File file) throws Exception{
        initializeFields();
        agentMap = new HashMap<>();
        clientMap = new HashMap<>();
        reader.loadBook(file);
        sourceData = reader.readSheet();
        fillColumnNumbers(fields);
        sourceData.removeFirst();
        String agentName;
        String clientID;
        for (LinkedList<String> list : sourceData) {
            agentName = list.get(columnNumbers.get("agentName"));
            clientID = list.get(columnNumbers.get("accessID"));
            if (!agentMap.containsKey(agentName)){
                agentMap.put(agentName, Agent.newAgent(list.get(columnNumbers.get("folderName")), list.get(columnNumbers.get("agentName"))));
            }
            if (!clientMap.containsKey(clientID)){
                clientMap.put(clientID, new Client.ClientBuilder()
                        .accessID(clientID)
                        .agent(agentMap.get(agentName))
                        .name(list.get(columnNumbers.get("name")))
                        .individualTaxpayerNumber(list.get(columnNumbers.get("individualTaxpayerNumber")))
                        .marketOwnerName(list.get(columnNumbers.get("marketOwnerName")))
                        .agent(agentMap.get(agentName))
                        .contractDate(parseDate(list.get(columnNumbers.get("contractDate"))))
                        .address(list.get(columnNumbers.get("address")))
                        .build());
            }
        }
        postToDB();
    }

    private void initializeFields() {
        columnNumbers = new HashMap<>();
        agentMap = new HashMap<>();
        clientMap = new HashMap<>();
    }

    private LocalDate parseDate(String date) {

        try {
             return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (Exception e) {
            try {
                return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MMM-yy"));
            }catch (Exception e1){
                return LocalDate.of(1991,01,01);
            }
        }
    }

    private void fillColumnNumbers(Map<String, Map<String, List<String>>> fields){

        Map<String, List<String>> sourceFields = fields.get(nameOfSourceColumnMap);
        for (String columnName: sourceData.getFirst()){
            for (Map.Entry entry: sourceFields.entrySet()) {
                if (((List<String>) entry.getValue()).contains(columnName)) {
                    columnNumbers.put((String) entry.getKey(), sourceData.getFirst().indexOf(columnName));
                }
            }
        }
    }

    private void postToDB() {
        agentRepository.saveAll(agentMap.values());
        clientRepository.saveAll(clientMap.values());
    }

}
