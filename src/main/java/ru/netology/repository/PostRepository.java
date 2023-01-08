package ru.netology.repository;

import ru.netology.model.Post;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
    private ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private AtomicLong postId = new AtomicLong(0);

    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.of(posts.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(postId.incrementAndGet());
            posts.put(postId.get(), post);
        } else if (post.getId() > 0 && posts.containsKey(post.getId())) {
            posts.replace(post.getId(), post);
        }
        return post;
    }

    public void removeById(long id) {
        if (posts.containsKey(id)) {
            posts.remove(id);
        }
    }
}
