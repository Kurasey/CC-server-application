package me.t.kaurami.applicationinfo;

import me.t.kaurami.data.AgentRepository;
import me.t.kaurami.data.ClientRepository;
import me.t.kaurami.data.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ApplicationInfo implements InfoContributor {

    private AgentRepository agentRepository;
    private ClientRepository clientRepository;
    private RequestRepository requestRepository;

    @Autowired
    public ApplicationInfo(AgentRepository agentRepository, ClientRepository clientRepository, RequestRepository requestRepository) {
        this.agentRepository = agentRepository;
        this.clientRepository = clientRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("stats", new HashMap<String, Object>(){{
            put("Agent count", agentRepository.count());
            put("Client count", clientRepository.count());
            put("Request count", requestRepository.count());
        }});
    }
}
