package com.casper.userslibrary.model.repository.wrapper;

import com.casper.userslibrary.model.entity.User;
import com.casper.userslibrary.model.repository.UserRepository;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserFileRepository implements UserRepository {
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

    private User parseUserFileText(String text) {
        Pattern splitPattern = Pattern.compile("\n\n");
        String[] userInfoParts = splitPattern.split(text);

        if (userInfoParts.length == 5) {
            String firstName = userInfoParts[0];
            String lastName = userInfoParts[1];
            String email = userInfoParts[2];

            splitPattern = Pattern.compile("\n");
            String[] roles = splitPattern.split(userInfoParts[3]);
            String[] phoneNumbers = splitPattern.split(userInfoParts[4]);

            return User
                    .builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .roles(roles)
                        .phoneNumbers(phoneNumbers)
                    .build();
        }

        return null;
    }

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

    @Override
    public void update(User user) {
        File usersDir = new File("users");

        if (usersDir.exists()) {
            String fileName = user.getId() + ".user";
            File userFile = new File(usersDir, fileName);
            saveUserToFile(user, userFile);
        }
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getById(long id) {
        File usersDir = new File("users");

        if (usersDir.exists()) {
            String fileName = id + ".user";
            File userFile = new File(usersDir, fileName);
            try {
                String userFileText = new String(Files.readAllBytes(Paths.get(userFile.getCanonicalPath())));
                User user = parseUserFileText(userFileText);

                if (user != null) {
                    user.setId(id);
                }

                return user;
            } catch (IOException ignored) {
            }
        }

        return null;
    }
}
