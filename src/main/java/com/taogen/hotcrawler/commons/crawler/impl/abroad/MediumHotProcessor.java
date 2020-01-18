package com.taogen.hotcrawler.commons.crawler.impl.abroad;//package com.taogen.hotcrawler.commons.crawler.impl.abroad;

import com.jayway.jsonpath.JsonPath;
import com.taogen.hotcrawler.commons.config.SiteProperties;
import com.taogen.hotcrawler.commons.constant.RequestMethod;
import com.taogen.hotcrawler.commons.crawler.APIHotProcessor;
import com.taogen.hotcrawler.commons.crawler.HotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("MediumHotProcessor")
public class MediumHotProcessor extends APIHotProcessor {
    @Autowired
    private SiteProperties siteProperties;

    @Autowired
    private ApplicationContext context;

    @Override
    @PostConstruct
    protected void initialize(){
        RequestMethod requestMethod = RequestMethod.POST;
        setFieldsByProperties(siteProperties, requestMethod, generateHeader(),generateRequestBody());
        injectBeansByContext(context);
        setLog(LoggerFactory.getLogger(getClass()));
    }

    @Override
    protected List<Info> getInfoDataByJson(String json) {
        List<Info> list = new ArrayList<>();
        if (json != null && json.length() > 0)
        {
            List<String> titles1 = JsonPath.read(json, "$.data.topic.featurePosts.postPreviews.[*].post.title");
            List<String> titles2 = JsonPath.read(json, "$.data.topic.latestPosts.postPreviews.[*].post.title");
            List<String> urls1 = JsonPath.read(json, "$.data.topic.featurePosts.postPreviews.[*].post.mediumUrl");
            List<String> urls2 = JsonPath.read(json, "$.data.topic.latestPosts.postPreviews.[*].post.mediumUrl");
            List<Info> indexInfoList = getInfoListByTitlesAndUrls(titles1, urls1);
            List<Info> indexInfoList2 = getInfoListByTitlesAndUrls(titles2, urls2);
            list.addAll(indexInfoList);
            list.addAll(indexInfoList2);
        }
        return list;
    }

    @Override
    protected Map<String, String> generateHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("Host", "medium.com");
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:69.0) Gecko/20100101 Firefox/69.0");
        header.put("Accept", "*/*");
        header.put("Accept-Language","en-US,en;q=0.5");
        header.put("Accept-Encoding", "gzip, deflate, br");
        header.put("Referer", "https://medium.com/topic/popular");
        header.put("content-type", "application/json");
        header.put("apollographql-client-name", "lite");
        header.put("Medium-Frontend-Route", "topic");
        header.put("Graphql-Operation", "TopicHandler");
        header.put("Origin", "https://medium.com");
        return header;
    }

    @Override
    protected String generateRequestBody() {
        return "{\"operationName\":\"TopicHandler\",\"variables\":{\"topicSlug\":\"popular\",\"feedPagingOptions\":{\"limit\":25,\"to\":\"1569442145573\"},\"sidebarPagingOptions\":{\"limit\":5}},\"query\":\"query TopicHandler($topicSlug: ID!, $feedPagingOptions: PagingOptions, $sidebarPagingOptions: PagingOptions) {\\n  topic(slug: $topicSlug) {\\n    canonicalSlug\\n    ...TopicScreen_topic\\n    __typename\\n  }\\n}\\n\\nfragment PostListingItemFeed_postPreview on PostPreview {\\n  post {\\n    ...PostListingItemPreview_post\\n    ...PostListingItemByline_post\\n    ...PostListingItemImage_post\\n    ...PostPresentationTracker_post\\n    __typename\\n  }\\n  __typename\\n}\\n\\nfragment PostListingItemPreview_post on Post {\\n  id\\n  mediumUrl\\n  title\\n  previewContent {\\n    subtitle\\n    isFullContent\\n    __typename\\n  }\\n  isPublished\\n  creator {\\n    id\\n    __typename\\n  }\\n  __typename\\n}\\n\\nfragment PostListingItemByline_post on Post {\\n  id\\n  creator {\\n    id\\n    username\\n    name\\n    __typename\\n  }\\n  isLocked\\n  readingTime\\n  ...BookmarkButton_post\\n  firstPublishedAt\\n  statusForCollection\\n  collection {\\n    id\\n    name\\n    ...collectionUrl_collection\\n    __typename\\n  }\\n  __typename\\n}\\n\\nfragment BookmarkButton_post on Post {\\n  ...SusiClickable_post\\n  ...WithSetReadingList_post\\n  __typename\\n}\\n\\nfragment SusiClickable_post on Post {\\n  id\\n  mediumUrl\\n  ...SusiContainer_post\\n  __typename\\n}\\n\\nfragment SusiContainer_post on Post {\\n  id\\n  __typename\\n}\\n\\nfragment WithSetReadingList_post on Post {\\n  ...ReadingList_post\\n  __typename\\n}\\n\\nfragment ReadingList_post on Post {\\n  id\\n  readingList\\n  __typename\\n}\\n\\nfragment collectionUrl_collection on Collection {\\n  id\\n  domain\\n  slug\\n  __typename\\n}\\n\\nfragment PostListingItemImage_post on Post {\\n  id\\n  mediumUrl\\n  previewImage {\\n    id\\n    focusPercentX\\n    focusPercentY\\n    __typename\\n  }\\n  __typename\\n}\\n\\nfragment PostPresentationTracker_post on Post {\\n  id\\n  visibility\\n  previewContent {\\n    isFullContent\\n    __typename\\n  }\\n  collection {\\n    id\\n    __typename\\n  }\\n  __typename\\n}\\n\\nfragment TopicScreen_topic on Topic {\\n  id\\n  ...TopicMetadata_topic\\n  ...TopicLandingHeader_topic\\n  ...TopicFeaturedAndLatest_topic\\n  ...TopicLandingRelatedTopics_topic\\n  ...TopicLandingPopular_posts\\n  __typename\\n}\\n\\nfragment TopicMetadata_topic on Topic {\\n  name\\n  description\\n  image {\\n    id\\n    __typename\\n  }\\n  __typename\\n}\\n\\nfragment TopicLandingHeader_topic on Topic {\\n  name\\n  description\\n  visibility\\n  ...TopicFollowButtonSignedIn_topic\\n  ...TopicFollowButtonSignedOut_topic\\n  __typename\\n}\\n\\nfragment TopicFollowButtonSignedIn_topic on Topic {\\n  slug\\n  isFollowing\\n  __typename\\n}\\n\\nfragment TopicFollowButtonSignedOut_topic on Topic {\\n  id\\n  slug\\n  ...SusiClickable_topic\\n  __typename\\n}\\n\\nfragment SusiClickable_topic on Topic {\\n  ...SusiContainer_topic\\n  __typename\\n}\\n\\nfragment SusiContainer_topic on Topic {\\n  ...SignInContainer_topic\\n  ...SignUpOptions_topic\\n  __typename\\n}\\n\\nfragment SignInContainer_topic on Topic {\\n  ...SignInOptions_topic\\n  __typename\\n}\\n\\nfragment SignInOptions_topic on Topic {\\n  id\\n  name\\n  __typename\\n}\\n\\nfragment SignUpOptions_topic on Topic {\\n  id\\n  name\\n  __typename\\n}\\n\\nfragment TopicFeaturedAndLatest_topic on Topic {\\n  featuredPosts {\\n    postPreviews {\\n      post {\\n        id\\n        ...TopicLandingFeaturedStory_post\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n  featuredTopicWriters(limit: 1) {\\n    ...FeaturedWriter_featuredTopicWriter\\n    __typename\\n  }\\n  latestPosts(paging: $feedPagingOptions) {\\n    postPreviews {\\n      post {\\n        id\\n        __typename\\n      }\\n      ...PostListingItemFeed_postPreview\\n      __typename\\n    }\\n    pagingInfo {\\n      next {\\n        limit\\n        to\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n  __typename\\n}\\n\\nfragment TopicLandingFeaturedStory_post on Post {\\n  ...FeaturedPostPreview_post\\n  ...PostListingItemPreview_post\\n  ...PostListingItemBylineWithAvatar_post\\n  ...PostListingItemImage_post\\n  ...PostPresentationTracker_post\\n  __typename\\n}\\n\\nfragment FeaturedPostPreview_post on Post {\\n  id\\n  title\\n  mediumUrl\\n  previewContent {\\n    subtitle\\n    isFullContent\\n    __typename\\n  }\\n  __typename\\n}\\n\\nfragment PostListingItemBylineWithAvatar_post on Post {\\n  creator {\\n    username\\n    name\\n    id\\n    imageId\\n    mediumMemberAt\\n    __typename\\n  }\\n  isLocked\\n  readingTime\\n  updatedAt\\n  statusForCollection\\n  collection {\\n    id\\n    name\\n    ...collectionUrl_collection\\n    __typename\\n  }\\n  __typename\\n}\\n\\nfragment FeaturedWriter_featuredTopicWriter on FeaturedTopicWriter {\\n  user {\\n    id\\n    username\\n    name\\n    bio\\n    ...UserAvatar_user\\n    ...UserFollowButton_user\\n    __typename\\n  }\\n  posts {\\n    postPreviews {\\n      ...PostListingItemFeaturedWriter_postPreview\\n      __typename\\n    }\\n    __typename\\n  }\\n  __typename\\n}\\n\\nfragment UserAvatar_user on User {\\n  username\\n  id\\n  name\\n  imageId\\n  mediumMemberAt\\n  __typename\\n}\\n\\nfragment UserFollowButton_user on User {\\n  ...UserFollowButtonSignedIn_user\\n  ...UserFollowButtonSignedOut_user\\n  __typename\\n}\\n\\nfragment UserFollowButtonSignedIn_user on User {\\n  id\\n  isFollowing\\n  __typename\\n}\\n\\nfragment UserFollowButtonSignedOut_user on User {\\n  id\\n  ...SusiClickable_user\\n  __typename\\n}\\n\\nfragment SusiClickable_user on User {\\n  ...SusiContainer_user\\n  __typename\\n}\\n\\nfragment SusiContainer_user on User {\\n  ...SignInContainer_user\\n  ...SignUpOptions_user\\n  __typename\\n}\\n\\nfragment SignInContainer_user on User {\\n  ...SignInOptions_user\\n  __typename\\n}\\n\\nfragment SignInOptions_user on User {\\n  id\\n  name\\n  __typename\\n}\\n\\nfragment SignUpOptions_user on User {\\n  id\\n  name\\n  __typename\\n}\\n\\nfragment PostListingItemFeaturedWriter_postPreview on PostPreview {\\n  postId\\n  post {\\n    readingTime\\n    id\\n    mediumUrl\\n    title\\n    ...PostListingItemImage_post\\n    ...PostPresentationTracker_post\\n    __typename\\n  }\\n  __typename\\n}\\n\\nfragment TopicLandingRelatedTopics_topic on Topic {\\n  relatedTopics {\\n    topic {\\n      name\\n      slug\\n      __typename\\n    }\\n    __typename\\n  }\\n  __typename\\n}\\n\\nfragment TopicLandingPopular_posts on Topic {\\n  name\\n  popularPosts(paging: $sidebarPagingOptions) {\\n    postPreviews {\\n      post {\\n        ...PostListingItemSidebar_post\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n  __typename\\n}\\n\\nfragment PostListingItemSidebar_post on Post {\\n  id\\n  mediumUrl\\n  title\\n  readingTime\\n  ...PostListingItemImage_post\\n  ...PostPresentationTracker_post\\n  __typename\\n}\\n\"}";
    }
}
