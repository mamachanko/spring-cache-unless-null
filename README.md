# spring-cache-unless-null

```java
    @Cacheable(value = "cacheable", unless = "#result == null")
    public String get(String id) {
        if (id == "unknown") {
            return null;
        }
        return UUID.randomUUID().toString();
    }
```

... including tests.
