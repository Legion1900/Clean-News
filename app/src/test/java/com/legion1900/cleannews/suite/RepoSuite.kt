package com.legion1900.cleannews.suite

import com.legion1900.cleannews.repo.CacheRepoTest
import com.legion1900.cleannews.repo.NewsRepoTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    CacheRepoTest::class,
    NewsRepoTest::class
)
class RepoSuite