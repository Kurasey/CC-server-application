package me.t.kaurami.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Entity representing a request from agents about shipment
 */
@Entity
@Table(name = "requests")
public class Request implements Comparable<Request> {

    /**
     * Version for optimistic lock
     */
    @Version
    private LocalDateTime version;

    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reqId;

    /**
     * Trade point
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_access_id")
    @NotNull
    private Client client;

    /**
     * Sales manager, also the source of request
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agent_name")
    @NotNull
    private Agent agent;
    /**
     * The essence of the request
     */
    @Column(name = "request_type")
    @Enumerated(EnumType.STRING)
    @NotNull
    private RequestType type;
    /**
     * Decision of request
     */
    @Column(name = "request_decision")
    @Enumerated(EnumType.STRING)
    private Decision decision;
    /**
     * Date and time of request create
     */
    @Column(name = "request_date", nullable = false, updatable = false)
    @NotNull
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestDate;
    /**
     * Date and time accept of decision
     */
    @Column(name = "decision_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @UpdateTimestamp
    private LocalDateTime decisionDate;
    /**
     * Commentary when creating
     */
    @Column(name = "request_commentary")
    private String commentary;
    /**
     * Commentary when decision-making
     */
    @Column(name = "decision_cause")
    private String decisionCause;

    public enum RequestType{
        LIMIT("Увеличение кредитного лимита"), STOPLIST("Снять стоп-лист"),
        SEQURITYORDER("Направить запрос в СБ"), OTHER("Другое");

        private String text;

        public static RequestType getByText(String text){
            for(RequestType type: RequestType.values()){
                if(type.text.equals(text)){
                    return type;
                }
            }
            return null;
        }

        RequestType(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public enum Decision{
        AGREED("Согласовано", true), DISAGREED("Отказ", true), APPROVAL("На согласовании", false), ND("", false);

        private String text;
        private boolean completed;

        Decision(String text, boolean completed) {
            this.text = text;
            this.completed = completed;
        }

        public static Decision getByText(String text){
            for (Decision d: Decision.values()){
                if(d.text.equals(text))
                    return d;
            }
            return null;
        }

        public boolean isCompleted() {
            return completed;
        }

        @Override
        public String toString() {
            return text;
        }

    }

    public static final class RequestBuilder{
        private Client client;
        private Agent agent;
        private RequestType type;
        private String commentary;

        public RequestBuilder client(Client client) {
            this.client = client;
            return this;
        }

        public RequestBuilder agent(Agent agent) {
            this.agent = agent;
            return this;
        }

        public RequestBuilder type(RequestType type) {
            this.type = type;
            return this;
        }

        public RequestBuilder commentary(String commentary) {
            this.commentary = commentary;
            return this;
        }

        public Request build(){
            return new Request(client, agent, type, commentary);
        }
    }

    public Request(Client client, Agent agent, RequestType type, String commentary) {
        this.reqId = 1l;
        this.client = client;
        this.agent = agent;
        this.type = type;
        this.decision = Decision.ND;
        this.commentary = commentary;
        this.requestDate = LocalDateTime.now();
    }

    private Request() {
    }

    public static RequestBuilder builder(){
        return new RequestBuilder();
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
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

    public LocalDateTime getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "Request{" +
                "version=" + version +
                ", reqId=" + reqId +
                ", client=" + client.getName() +
                ", agent=" + agent.getShortName();
    }
}
