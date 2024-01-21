package com.code4alex.checkers.services;

import java.util.Deque;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.code4alex.checkers.config.ThreadConfig;
import com.code4alex.checkers.models.BoardNode;
import com.code4alex.checkers.workers.WorkerThread;

import lombok.val;

@Service
public class MoveProcessorService {

    @Autowired
    private ThreadConfig threadConfig;

    private Deque<BoardNode> processingStack = new LinkedList<>();
    private WorkerThread list[];

    public synchronized void pushNode(BoardNode node) {
        processingStack.push(node);
    }

    public synchronized BoardNode popNode() {
        return processingStack.pop();
    }

    public void startWorkerThreads() {
        list = new WorkerThread[8];
        for (int i = 0; i < threadConfig.getThreadPoolSize(); i++) {
            list[i] = new WorkerThread();
            Thread.ofVirtual().name(String.format("Worker Thread : %d", i)).start(list[i]);
        }
    }

    public void stopWorkerThreads() {
        for(val worker: list) {
            worker.stop();
        }
    }
}
