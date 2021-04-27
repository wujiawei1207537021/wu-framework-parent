package com.wu.framework.inner.layer.stereotype.analyze;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description 解析适配器 选举
 *
 * @author 吴佳伟
 * @date 2021/4/26 4:32 下午
 */
public class LayerAnalyzeAdapter {

    private final List<LayerAnalyze> layerAnalyzeList;

    private ConcurrentHashMap<AnalyzeParameter, LayerAnalyze> concurrentHashMap = new ConcurrentHashMap();

    public LayerAnalyzeAdapter(List<LayerAnalyze> layerAnalyzeList) {
        this.layerAnalyzeList = layerAnalyzeList;
    }


    boolean supportsParameter(AnalyzeParameter analyzeParameter) {
        return true;
    }


    Object  resolveLayerAnalyze(AnalyzeParameter analyzeParameter) {
        if (!concurrentHashMap.containsKey(analyzeParameter)) layerAnalyzeList.forEach(layerAnalyze -> {
            if (layerAnalyze.supportParameter(analyzeParameter)) {
                concurrentHashMap.put(analyzeParameter, layerAnalyze);
            }
        });
        return concurrentHashMap.get(analyzeParameter).analyze(analyzeParameter);
    }
}
