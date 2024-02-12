package com.code4alex.checkers.services;

import java.util.Deque;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.code4alex.checkers.config.ThreadConfig;
import com.code4alex.checkers.models.BoardNode;
import com.code4alex.checkers.workers.WorkerThread;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

@Service
public class MoveProcessorService {

    @Autowired
    private ThreadConfig threadConfig;

    private Deque<BoardNode> processingStack = new LinkedList<>();
    private WorkerThread list[];
    @Getter @Setter
    private int moveLimit = 5;

    public synchronized void pushNode(BoardNode node) {
        processingStack.addLast(node);
    }

    public synchronized BoardNode popNode() {
        if(processingStack.isEmpty() || processingStack.peek().getMoveNumber() - moveLimit >= 0) {
            return null;
        } else {
            return processingStack.pop();
        }
    }

    public void startWorkerThreads() {
        list = new WorkerThread[threadConfig.getThreadPoolSize()];
        for (int i = 0; i < threadConfig.getThreadPoolSize(); i++) {
            list[i] = new WorkerThread(this);
            Thread.ofVirtual().name(String.format("Worker Thread : %d", i)).start(list[i]);
        }
    }

    public void stopWorkerThreads() {
        for(val worker: list) {
            worker.stop();
        }
    }
}
