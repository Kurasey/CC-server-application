package me.t.kaurami.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Client {

    @Id
    private String accessID;

    private Agent agent;

    private String name;
    private String individualTaxpayerNumber;
    private String marketOwnerName;
    private String agentName;
    private String folderName;
    private LocalDate contractDate;
    private String address;

    private Client(String name, String individualTaxpayerNumber, String accessId, String marketOwnerName,
                   String agentName, String folderName, LocalDate contractDate, String address, Agent agent) {
        this.name = name;
        this.individualTaxpayerNumber = individualTaxpayerNumber;
        this.accessID = accessId;
        this.marketOwnerName = marketOwnerName;
        this.agentName = agentName;
        this.folderName = folderName;
        this.contractDate = contractDate;
        this.address = address;
        this.agent = agent;
    }

    public static class ClientBuilder{

        private String accessID;
        private Agent agent;
        private String name;
        private String individualTaxpayerNumber;
        private String marketOwnerName;
        private String agentName;
        private String folderName;
        private LocalDate contractDate;
        private String address;

        public ClientBuilder() {
        }

        public Client build(){
            return new Client(name, individualTaxpayerNumber, accessID,
                    marketOwnerName, agentName, folderName, contractDate, address, agent);
        };

        public ClientBuilder accessID(String accessID){
            this.accessID = accessID;
            return this;
        }
        public ClientBuilder agent(Agent agent){
            this.agent = agent;
            return this;
        }
        public ClientBuilder name(String name){
            this.name = name;
            return this;
        }
        public ClientBuilder individualTaxpayerNumber(String individualTaxpayerNumber){
            this.individualTaxpayerNumber = individualTaxpayerNumber;
            return this;
        }
        public ClientBuilder marketOwnerName(String marketOwnerName){
            this.marketOwnerName = marketOwnerName;
            return this;
        }
        public ClientBuilder agentName(String agentName){
            this.agentName = agentName;
            return this;
        }
        public ClientBuilder folderName(String folderName){
            this.folderName = folderName;
            return this;
        }
        public ClientBuilder contractDate(LocalDate contractDate){
            this.contractDate = contractDate;
            return this;
        }
        public ClientBuilder address(String address){
            this.address = address;
            return this;
        }



    }

    public String getAccessID() {
        return accessID;
    }


    public String getName() {
        return name;
    }

    public String getIndividualTaxpayerNumber() {
        return individualTaxpayerNumber;
    }

    public String getMarketOwnerName() {
        return marketOwnerName;
    }

    public String getAgentName() {
        return agentName;
    }

    public String getFolderName() {
        return folderName;
    }

    public LocalDate getContractDate() {
        return contractDate;
    }

    public String getAddress() {
        return address;
    }

    public Agent getAgent() {
        return agent;
    }

    @Override
    public String toString() {
        return "Client{" +
                "accessID='" + accessID + '\'' +
                ", agent=" + agent +
                ", name='" + name + '\'' +
                ", individualTaxpayerNumber='" + individualTaxpayerNumber + '\'' +
                ", marketOwnerName='" + marketOwnerName + '\'' +
                ", agentName='" + agentName + '\'' +
                ", folderName='" + folderName + '\'' +
                ", contractDate=" + contractDate +
                ", address='" + address + '\'' +
                '}';
    }
}
