package Konkuk.U2E.domain.news.service.mapper;

import Konkuk.U2E.domain.news.domain.ClimateProblem;
import Konkuk.U2E.domain.news.domain.News;

import java.util.List;

public record NewsMappingResult(
        List<ClimateProblem> climateProblems,
        List<String> regionNames,
        News news
) {}
