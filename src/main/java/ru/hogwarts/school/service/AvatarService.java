package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.repositories.AvatarRepository;

import java.io.IOException;
import java.util.Collection;

public interface AvatarService {

    void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;

    Avatar findAvatar(Long studentId);

    Avatar findOrCreate(Long studentId);

    Collection<Avatar> getPage(Integer pageNumber, Integer pageSize);
}