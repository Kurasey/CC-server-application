package me.t.kaurami.entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Agent {
    private static Agent nullAgent = new Agent();

    @Id
    private String agentName;
    private String folderName;
    private String shortName;
    private boolean isIntruder;


    public static Agent newAgent(String folderName, String fullName) {
        String[] arg = fullName.split("\\d+");
        if (arg.length > 1) {
            if (arg[1].trim().length() > 1){
                String shortName = arg[1].trim().substring(0, arg[1].trim().indexOf(" ") +2);
                return new Agent(folderName, fullName, shortName);
            }
        }
        return nullAgent;
    }

    private Agent() {
    }

    private Agent(String folderName, String fullName, String shortName){
        this.folderName = folderName;
        this.agentName = fullName;
        this.shortName = shortName;
        this.isIntruder = false;
    }



    @Override
    public String toString() {
        return "Agent{" +
                "agentName='" + agentName + '\'' +
                ", folderName='" + folderName + '\'' +
                '}';
    }

    public String getAgentName() {
        return agentName;
    }

    public String getFolderName() {
        return folderName;
    }

    public String getShortName() {
        return shortName;
    }
}
