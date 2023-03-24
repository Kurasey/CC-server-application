package me.t.kaurami.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Request implements Comparable<Request> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reqId;

    @OneToOne
    private Client client;

    @OneToOne
    private Agent agent;

    private RequestType type;
    private Decision decision;
    private LocalDateTime requestDate;
    private LocalDateTime decisionDate;
    private String commentary;
    private String decisionCause;

    public enum RequestType{
        LIMIT("Увеличение кредитного лимита"), STOPLIST("Снять стоп-лист"),
        SEQURITYORDER("Направить запрос в СБ"), OTHER("Другое");


        private String name;

        RequestType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum Decision{
        AGREED("Согласовано", true), DISAGREED("Отказ", true), APPROVAL("На согласовании", false), ND("", false);

        private String name;
        private boolean completed;

        Decision(String name, boolean completed) {
            this.name = name;
            this.completed = completed;
        }

        public boolean isCompleted() {
            return completed;
        }

        @Override
        public String toString() {
            return name;
        }

    }

    public Request(Client client, Agent agent, RequestType type, String commentary) {
        this.client = client;
        this.agent = agent;
        this.type = type;
        this.commentary = commentary;
        this.requestDate = LocalDateTime.now();
    }

    private Request() {
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }

    public String getClientAccessId() {
        return clientAccessId;
    }

    public String getAgentName() {
        return agentName;
    }

    public RequestType getType() {
        return type;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public Decision getDecision() {
        return decision;
    }

    public LocalDateTime getDecisionDate() {
        return decisionDate;
    }

    @Override
    public int compareTo(Request o) {
        return requestDate.compareTo(o.getRequestDate());
    }

    public void setDecisionCause(String decisionCause) {
        this.decisionCause = decisionCause;
    }

    public Long getReqId() {
        return reqId;
    }

    public Client getClient() {
        return client;
    }

    public Agent getAgent() {
        return agent;
    }

    public String getCommentary() {
        return commentary;
    }

    public String getDecisionCause() {
        return decisionCause;
    }

    public void setReqId(Long reqId) {
        this.reqId = reqId;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public void setType(String type) {
        this.type = RequestType.valueOf(type);
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public void setDecisionDate(LocalDateTime decisionDate) {
        this.decisionDate = decisionDate;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }
}
