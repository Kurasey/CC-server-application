package me.t.kaurami;

import me.t.kaurami.data.AgentRepository;
import me.t.kaurami.entities.Agent;
import me.t.kaurami.service.bookReader.BookReader;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class TestAgent {
    public static TestAgent testAgent;
    private AgentRepository agentRepository;

    private BookReader bookReader;

    public TestAgent(AgentRepository agentRepository, BookReader bookReader) {
        this.agentRepository = agentRepository;
        this.bookReader = bookReader;
        testAgent = this;
    }

    public void setAgents(){
        agentRepository.save(new Agent(
                "Агент Корма Кр.-012 Симановский Александр Викторович",
                "ПККра 012 Симановский Александр Викторович_П012",
                "Ананков"));
        agentRepository.save(new Agent(
                "Аг Бел_009 Гринько Светлана Сергеевна",
                "Гринько Светлана Сергеевна_Н009",
                "Алферов"));
        agentRepository.save(new Agent(
                "Аг Арм_005 Деренко Андрей Дмитриевич",
                "Деренко Андрей Дмитриевич_Н005",
                "Алферов"));
        agentRepository.save(new Agent(
                "Аг Кр_043 Красникова Ольга Георгиевна",
                "Красникова Ольга Георгиевна_Н043",
                "Кузнецов"));
        agentRepository.save(new Agent(
                "Аг Арм_004 Родина Валентина Юрьевна",
                "Родина Валентина Юрьевна_Н004",
                "Алферов"));
    }
}
