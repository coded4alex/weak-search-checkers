package com.code4alex.checkers.beans;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import lombok.val;

import com.code4alex.checkers.models.Board;
import com.code4alex.checkers.models.BoardNode;
import com.code4alex.checkers.services.MoveProcessorService;

@SpringBootApplication
public class CommandLineInterface {
    @Autowired
    private MoveProcessorService moveProcessorService;

    @Bean
	public CommandLineRunner bootUp(ApplicationContext applicationContext) {
        return args -> {
			val boardNode = BoardNode.builder()
				.board(Board.newBoard())
				.moveNumber(0)
				.pruned(false)
				.score(0)
				.treeNodes(new ArrayList<>())
				.build();
			moveProcessorService.setMoveLimit(8);
            moveProcessorService.pushNode(boardNode);
            moveProcessorService.startWorkerThreads();
			Thread.sleep(1000*10);
            printBoards(boardNode);
        };
    }

    private void printBoards(BoardNode boardNode) {
        System.out.println(boardNode.getBoard());
        for(val boards: boardNode.getTreeNodes()) {
            printBoards(boards);
        }
    }
}
