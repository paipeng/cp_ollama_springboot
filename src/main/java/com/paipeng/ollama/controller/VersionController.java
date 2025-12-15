package com.paipeng.ollama.controller;

import com.paipeng.ollama.config.ApplicationConfig;
import com.paipeng.ollama.config.VersionConfig;
import com.paipeng.ollama.model.Version;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Tag(name="Version", description = "Version API")
@Controller
public class VersionController {
    private final Logger logger = LoggerFactory.getLogger(VersionController.class);

    @Autowired
    private VersionConfig versionConfig;

    @Autowired
    private ApplicationConfig applicationConfig;

    @GetMapping("/version")
    public Version version() {
        logger.info("version");
        Version version = new Version();

        version.setName(versionConfig.getName());
        version.setVersion(versionConfig.getVersion());
        version.setCreateDate(versionConfig.getCreateDate());
        version.setTime(stampToDate(Calendar.getInstance().getTimeInMillis()));
        return version;
    }

    private String stampToDate(long a) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(a);
        return simpleDateFormat.format(date);
    }
}
