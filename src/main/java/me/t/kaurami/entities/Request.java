package me.t.kaurami.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "requests")
public class Request implements Comparable<Request> {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long reqId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_access_id")
    @NotNull
    private Client client;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agent_name")
    @NotNull
    private Agent agent;

    @Column(name = "request_type")
    @Enumerated(EnumType.STRING)
    @NotNull
    private RequestType type;

    @Column(name = "request_decision")
    @Enumerated(EnumType.STRING)
    private Decision decision;

    @Column(name = "request_date")
    @NotNull
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime requestDate;

    @Column(name = "decision_date")
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime decisionDate;

    @Column(name = "request_commentary")
    private String commentary;

    @Column(name = "decision_cause")
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
        this.reqId = 1l;
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

/*    public String getClientAccessId() {
        return clientAccessId;
    }

    public String getAgentName() {
        return agentName;
    }*/

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
