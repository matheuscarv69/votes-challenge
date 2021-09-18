package backvoteschallenge.entities.order.entity;

import backvoteschallenge.entities.associate.entity.associate.Associate;
import backvoteschallenge.entities.vote.entity.TypeVote;
import backvoteschallenge.entities.vote.entity.Vote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    @DisplayName("Deve retornar true caso o Associado já tenha votado na Pauta")
    void testDeveRetornarTrueSeAssociadoJaVotouNaPauta() {

        var associado = criaAssociado();
        var pauta = criaPauta();
        var vote = criaVoto();

        pauta.addVote(vote);

        Assertions.assertTrue(pauta.associateAlreadyVoted(associado));
    }

    @Test
    @DisplayName("Deve retornar false caso o Associado não tenha votado na Pauta")
    void testDeveRetornarFalseSeAssociadoNaoVotouNaPauta() {

        var pauta = criaPauta();
        var vote = criaVoto();

        pauta.addVote(vote);

        Assertions.assertFalse(pauta.associateAlreadyVoted(new Associate()));
    }

    private Vote criaVoto() {
        Vote vote = new Vote();
        vote.setId(1L);
        vote.setTypeVote(TypeVote.Nao);
        vote.setOrder(criaPauta());
        vote.setAssociate(criaAssociado());

        return vote;
    }


    private Order criaPauta() {
        Order order = new Order();
        order.setId(1L);
        order.setTheme("Clean Code is great?");
        order.setEditor("Uncle Bob");

        return order;
    }

    private Associate criaAssociado() {
        Associate associate = new Associate();
        associate.setId(1L);
        associate.setName("Robert C. Martin");
        associate.setDocument("52185458051");

        return associate;
    }
}