# hot-crawler

[![GitHub issues](https://img.shields.io/github/issues/tagnja/hot-crawler)](https://github.com/tagnja/hot-crawler/issues)
[![GitHub stars](https://img.shields.io/github/stars/tagnja/hot-crawler)](https://github.com/tagnja/hot-crawler/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/tagnja/hot-crawler)](https://github.com/tagnja/hot-crawler/network)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d25aed8571b944e6838686d96ea3873f)](https://www.codacy.com/manual/tagnja/hot-crawler?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=tagnja/hot-crawler&amp;utm_campaign=Badge_Grade)
[![codecov](https://codecov.io/gh/tagnja/hot-crawler/branch/master/graph/badge.svg)](https://codecov.io/gh/tagnja/hot-crawler)
[![Build Status](https://travis-ci.com/tagnja/hot-crawler.svg?branch=test)](https://travis-ci.com/tagnja/hot-crawler)

语言: [English](README.md) | [中文简体](README_zh_CN.md)

##  What's hot-crawler

> 汇集热点内容，一站式阅读体验。

**hot-cralwer** 是一个关于优秀网站的热点资讯爬虫。它可以帮助你快速获取多个网站有价值的信息。它兼容多个终端设备，如个人电脑，手机，和平板电脑等。爬取的网站主要由两种语言组成：中文和英文。具体样式如下图所示。

![网站首页图](documents/hotcrawler-homepage-v2-pc.png)

## How to develop

### 开始之前

开发 hot-cralwer 之前，你需要安装以下软件：

- Git
- JDK
- Maven
- Redis

强烈建议使用 [IntelliJ IDEA](https://www.jetbrains.com/idea/?fromMenu) 进行开发。


## How to run
### 通过 Maven 命令行运行

```shell
# Download
$ git clone https://github.com/tagnja/hot-crawler.git
# Running redis in your computer
$ ./redis-server
# Running project
$ cd hot-crawler
$ mvn spring-boot:run
```

## Document

[网站爬虫快速指南](documents/crawler-development-tutorial_zh_CN.md)

[使用技术列表](documents/techniques-list_zh_CN.md)

[网站爬取列表](documents/websites-list_zh_CN.md)

[更新日志](documents/update_log.md)

## Contributing

欢迎大家对本开源项目进行贡献！详细说明请见[项目贡献指南](CONTRIBUTING_zh_CN.md)。

## License

本项目使用 [MIT License](https://opensource.org/licenses/MIT).