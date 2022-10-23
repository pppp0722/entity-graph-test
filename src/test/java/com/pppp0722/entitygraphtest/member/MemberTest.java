package com.pppp0722.entitygraphtest.member;

import com.pppp0722.entitygraphtest.post.Post;
import com.pppp0722.entitygraphtest.thumbnail.Thumbnail;
import java.util.List;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void test() {
        Member member1 = Member.builder()
            .name("member1")
            .build();
        em.persist(member1);

        Post post1 = Post.builder()
            .title("post1")
            .content("content1")
            .member(member1)
            .build();
        em.persist(post1);

        Post post2 = Post.builder()
            .title("post2")
            .content("content2")
            .member(member1)
            .build();
        em.persist(post2);

        Thumbnail thumbnail1 = Thumbnail.builder()
            .url("url1")
            .post(post1)
            .build();
        em.persist(thumbnail1);

        Thumbnail thumbnail2 = Thumbnail.builder()
            .url("url2")
            .post(post2)
            .build();
        em.persist(thumbnail2);

        em.clear();

        TypedQuery<Member> query = em.createQuery("from Member", Member.class);
        EntityGraph<Member> entityGraph = em.createEntityGraph(Member.class);
        entityGraph.addAttributeNodes("posts");
        entityGraph.addSubgraph("posts").addAttributeNodes("thumbnail");
        query.setHint("javax.persistence.fetchgraph", entityGraph);

        List<Member> members = query.getResultList();
        for (Member member : members) {
            List<Post> posts = member.getPosts();
            for (Post post : posts) {
                Thumbnail thumbnail = post.getThumbnail();
                System.out.println(
                    member.getName() + " " + post.getTitle() + " " + thumbnail.getUrl());
            }
        }
    }
}