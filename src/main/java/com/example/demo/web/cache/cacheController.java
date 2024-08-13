package com.example.demo.web.cache;

import com.example.demo.domain.cache.CacheService;
import com.example.demo.domain.issue.IssueEntity;
import com.example.demo.domain.issue.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cache")
@RequiredArgsConstructor
public class cacheController {

    private final IssueService issueService;
    private final CacheService cacheService;

    @GetMapping
    public String showIndex(Model model) {
        // 一旦キャッシュに読み込み、詳細画面で読み込めることを確認する
        List<IssueEntity> issues = issueService.findAll();
        for (IssueEntity issue : issues) {
            cacheService.init(issue);
        }

        model.addAttribute("issueList", issues);
        return "cache/index";
    }

    @GetMapping("/{issueId}")
    public String showDetailCache(@PathVariable("issueId") long issueId, Model model) {
        model.addAttribute("issue", cacheService.reload(issueId));
        return "cache/detail";
    }
}
