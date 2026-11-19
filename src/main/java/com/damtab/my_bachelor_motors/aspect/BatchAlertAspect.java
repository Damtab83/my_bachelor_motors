package com.damtab.my_bachelor_motors.aspect;

import com.damtab.my_bachelor_motors.service.AlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class BatchAlertAspect {


    private final AlertService alertService;

    @Around("@annotation(alertOnFailure)")
    public Object monitorBatch(
            ProceedingJoinPoint joinPoint,
            AlertOnFailure alertOnFailure
    ) throws Throwable {

        try {
            return joinPoint.proceed();

        } catch (Throwable ex) {

            String batchName = alertOnFailure.value().isBlank()
                    ? joinPoint.getSignature().getName()
                    : alertOnFailure.value();

            alertService.sendAlert(
                    "BATCH",
                    "Batch failed: " + batchName,
                    "Le traitement batch a échoué.\n" +
                            "Classe : " + joinPoint.getTarget()
                            .getClass().getSimpleName() +
                            "\nErreur : " + ex.getMessage()
            );

            throw ex;
        }
    }
}
