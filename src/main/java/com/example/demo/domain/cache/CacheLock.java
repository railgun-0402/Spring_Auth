package com.example.demo.domain.cache;

import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

@Component
public class CacheLock {
    private final ReentrantReadWriteLock.ReadLock readLock;
    private final ReentrantReadWriteLock.WriteLock writeLock;

    public CacheLock() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
        readLock = lock.readLock();
        writeLock = lock.writeLock();
    }

    // キャッシュ更新(書き込み)
    public void write(Runnable runnable) {
        writeLock.lock();
        try {
            runnable.run();
        } finally {
            writeLock.unlock();
        }
    }

    // キャッシュ更新(読み込み)
    public <T> T read(Supplier<T> supplier) {
        readLock.lock();
        try {
            return supplier.get();
        } finally {
            readLock.unlock();
        }
    }
}
