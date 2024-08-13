package com.example.demo.domain.cache;

import com.example.demo.domain.issue.IssueEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CacheService {
    private final CacheLock cacheLock;
    private final Map<Long, IssueEntity> cache = new HashMap<>();

    /* Constructor */
    public CacheService(CacheLock cacheLock) {
        this.cacheLock = cacheLock;
    }

    // キャッシュにissue情報を全て突っ込む
    public void init(IssueEntity issue) {
        cacheLock.write(() ->  cache.put(1L, issue));
    }

    // キャッシュの値がnull出なければそっちからとってくる
    public IssueEntity reload(long id) {
        return cacheLock.read(() -> cache.get(id));
    }

    // IDでキャッシュからIssueEntityを削除
    public void remove(long id) {
        cacheLock.write(() -> cache.remove(id));
    }
}
