package com.code4alex.checkers.workers;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code4alex.checkers.models.BoardNode;
import com.code4alex.checkers.services.MoveProcessorService;
import com.code4alex.checkers.utils.BoardGenerator;

import lombok.val;

public class WorkerThread implements Runnable {
    private MoveProcessorService moveProcessorService;
    private boolean running = true;
    Logger logger = LoggerFactory.getLogger(WorkerThread.class);

    public WorkerThread(MoveProcessorService moveProcessorService) {
        this.moveProcessorService = moveProcessorService;
    }

    @Override
    public void run() {
        BoardNode current;
        BoardGenerator boardGenerator = new BoardGenerator();
        while(running) {
            current = moveProcessorService.popNode();
            if(current == null) {
                try {
                    Thread.sleep(500);
                    logger.info("Waiting");
                    continue;
                } catch (Exception e) {
                    return;
                }
            }
            ArrayList<BoardNode> futureBoards = boardGenerator.generateFutureBoardNodes(current, current.getNextPiece());
            current.addNodes(futureBoards);
            for(val i: futureBoards){
                moveProcessorService.pushNode(i);
            }
        }
    }

    public void stop() {
        running = false;
    }    
}
