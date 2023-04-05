package me.t.kaurami.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @Column(name = "access_id")
    @NotNull
    private String accessID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agent_name")
    @NotNull
    private Agent agent;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "individual_taxpayer_number")
    private String individualTaxpayerNumber;

    @Column(name = "market_owner_name")
    private String marketOwnerName;

    @Column(name = "contract_date")
    private LocalDate contractDate;

    @Column(name = "address")
    @NotBlank
    private String address;

    private Client(String name, String individualTaxpayerNumber, String accessId, String marketOwnerName,
                   LocalDate contractDate, String address, Agent agent) {
        this.name = name;
        this.individualTaxpayerNumber = individualTaxpayerNumber;
        this.accessID = accessId;
        this.marketOwnerName = marketOwnerName;
        this.contractDate = contractDate;
        this.address = address;
        this.agent = agent;
    }

    private Client(){
    }

    public static class ClientBuilder{

        private String accessID;
        private Agent agent;
        private String name;
        private String individualTaxpayerNumber;
        private String marketOwnerName;
        private LocalDate contractDate;
        private String address;

        public ClientBuilder() {
        }

        public Client build(){
            return new Client(name, individualTaxpayerNumber, accessID,
                    marketOwnerName, contractDate, address, agent);
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
        return agent.getAgentName();
    }

    public String getFolderName() {
        return agent.getFolderName();
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
                ", contractDate=" + contractDate +
                ", address='" + address + '\'' +
                '}';
    }
}
