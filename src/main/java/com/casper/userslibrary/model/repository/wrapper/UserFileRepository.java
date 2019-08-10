package com.casper.userslibrary.model.repository.wrapper;

import com.casper.userslibrary.model.entity.User;
import com.casper.userslibrary.model.repository.UserRepository;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserFileRepository implements UserRepository {
    @Override
    public void save(User user) {
        File usersDir = new File("users");

        if (usersDir.exists()) {
            FileFilter fileFilter = file -> file.getName().endsWith(".user");
            File[] userFiles = usersDir.listFiles(fileFilter);

            if (userFiles != null) {
                long lastUserId = getLastUserId(userFiles);

                if (lastUserId != -1) {
                    String newUserFileName = (lastUserId + 1) + ".user";
                    File userFile = new File(usersDir, newUserFileName);
                    saveUserToFile(user, userFile);
                }
            }
        } else if (usersDir.mkdir()) {
            File userFile = new File(usersDir, "1.user");
            saveUserToFile(user, userFile);
        }
    }

    private long getLastUserId(File[] userFiles) {
        long[] idArray = new long[userFiles.length];

        for (int i = 0; i < userFiles.length; i++) {
            Pattern idPattern = Pattern.compile("(\\d+)\\.user");
            Matcher idMatcher = idPattern.matcher(userFiles[i].getName());

            if (idMatcher.find()) {
                idArray[i] = Long.parseLong(idMatcher.group(1));
            }
        }

        if (idArray.length > 0) {
            Arrays.sort(idArray);
            return idArray[idArray.length - 1];
        } else {
            return -1;
        }
    }

    private void saveUserToFile(User user, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);

            fileWriter.write(user.getFirstName() + "\n\n" +
                    user.getLastName() + "\n\n" +
                    user.getEmail() + "\n");

            for (String role : user.getRoles()) {
                fileWriter.write("\n" + role);
            }

            fileWriter.write("\n");

            for (String phoneNumber : user.getPhoneNumbers()) {
                fileWriter.write("\n" + phoneNumber);
            }

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ignored) {
        }
    }

    @Override
    public void update(User user) {

    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getById(long id) {
        return null;
    }
}
