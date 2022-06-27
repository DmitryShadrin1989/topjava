package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        meal.setUser(em.getReference(User.class, userId));
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else if (get(meal.getId(), userId) != null) {
            return em.merge(meal);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        Meal meal = get(id, userId);
        if (meal != null) {
            Query query = em.createQuery("DELETE FROM Meal m WHERE m.id=:id AND m.user=: user");
            query.setParameter("id", id);
            query.setParameter("user", meal.getUser());
            return query.executeUpdate() != 0;
        } else {
            return false;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = em.find(Meal.class, id);
        if (meal != null && meal.getUser().getId() == userId) {
            return meal;
        } else {
            return null;
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        Query query = em.createQuery("SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC ");
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        Query query = em.createQuery("SELECT m " +
                "FROM Meal m " +
                "WHERE m.user.id=:userId AND m.dateTime >=:startDateTime AND m.dateTime<:endDateTime " +
                "ORDER BY m.dateTime DESC");
        query.setParameter("userId", userId);
        query.setParameter("startDateTime", startDateTime);
        query.setParameter("endDateTime", endDateTime);
        return query.getResultList();
    }
}