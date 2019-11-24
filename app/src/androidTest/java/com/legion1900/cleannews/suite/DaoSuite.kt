package com.legion1900.cleannews.suite

import com.legion1900.cleannews.dao.ArticleDaoTest
import com.legion1900.cleannews.dao.CacheDataDaoTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    ArticleDaoTest::class,
    CacheDataDaoTest::class
)
class DaoSuite