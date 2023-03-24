package me.t.kaurami.service.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UpdatingDbListener implements ApplicationListener<UpdatingDbEvent> {



    @Override
    public void onApplicationEvent(UpdatingDbEvent event) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Method is not implemented");
    }


}
