package me.t.kaurami.service.databaseplaseholder;

import me.t.kaurami.data.AgentRepository;
import me.t.kaurami.data.ClientRepository;
import me.t.kaurami.entities.Agent;
import me.t.kaurami.entities.Client;
import me.t.kaurami.service.bookReader.BookReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.Resource;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Database placeholder. Facade for extract data from .xls or .xlsx workbook, create entities and save them to database
 */
@Service
@RequestScope
public class ExcelDataBasePlaceholder {

    private BookReader reader;
    private AgentRepository agentRepository;
    private ClientRepository clientRepository;
    private List<List<String>> sourceData;
    private Map<String, Integer> columnNumbers;
    private Map<String, Agent> agentMap;
    private Map<String, Client> clientMap;

    @Resource(name = "fields")
    private Map<String, Map<String, List<String>>> fields;
    private String nameOfSourceColumnMap = "CLIENTS_LIST";


    @Autowired
    public ExcelDataBasePlaceholder(BookReader reader, AgentRepository agentRepository, ClientRepository clientRepository) {
        this.reader = reader;
        this.agentRepository = agentRepository;
        this.clientRepository = clientRepository;
    }

    private ExcelDataBasePlaceholder() {
    }


    /**
     * @param reader BookReader. Consumer of source data from file
     */
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

    /**
     * Full cycle of extract data and fill database
     * @param file
     * @throws Exception
     */
    public void uploadData(File file) throws Exception{
        initializeFields();
        agentMap = new HashMap<>();
        clientMap = new HashMap<>();
        reader.loadBook(file);
        sourceData = reader.readSheet();
        fillColumnNumbers(fields);
        sourceData.remove(0);
        String agentName;
        String clientID;
        for (List<String> list : sourceData) {
            agentName = list.get(columnNumbers.get("agentName"));
            clientID = list.get(columnNumbers.get("accessID"));
            if (!agentMap.containsKey(agentName)){
                agentMap.put(agentName, Agent.newAgent(list.get(columnNumbers.get("folderName")), list.get(columnNumbers.get("agentName"))));
            }
            if (!clientMap.containsKey(clientID)){
                clientMap.put(clientID, Client.builder()
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
        for (String columnName: sourceData.get(0)){
            for (Map.Entry entry: sourceFields.entrySet()) {
                if (((List<String>) entry.getValue()).contains(columnName)) {
                    columnNumbers.put((String) entry.getKey(), sourceData.get(0).indexOf(columnName));
                }
            }
        }
    }

//    @Transactional
//    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 100))
    private void postToDB() {
        agentRepository.saveAll(agentMap.values());
        clientRepository.saveAll(clientMap.values());
    }

}
