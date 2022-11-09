package com.pppp0722.entitygraphtest.many;

import com.pppp0722.entitygraphtest.one.One;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ManyTest {

    @Autowired
    EntityManager em;

    @Autowired
    ManyRepository manyRepository;

    @Test
    void test() {
        One one1 = One.builder()
            .name("one1")
            .build();
        em.persist(one1);

        Many many1 = Many.builder()
            .name("many1")
            .one(one1)
            .build();
        em.persist(many1);

        Many many2 = Many.builder()
            .name("many2")
            .one(one1)
            .build();
        em.persist(many2);

        em.clear();

        Many receivedMany1 = manyRepository.findById(1L)
            .orElseThrow(RuntimeException::new);
        Many receivedMany2 = manyRepository.findById(2L)
            .orElseThrow(RuntimeException::new);

        System.out.println(receivedMany1.getName() + " " + receivedMany1.getOne().getName());
        System.out.println(receivedMany2.getName() + " " + receivedMany2.getOne().getName());
    }
}