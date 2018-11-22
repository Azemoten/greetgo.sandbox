package kz.greetgo.sandbox.backend.util;

import kz.greetgo.sandbox.backend.controller.BeanScannerController;
import kz.greetgo.sandbox.backend.service.impl.BeanScannerService;
import org.springframework.context.annotation.Import;

@Import({
    BeanScannerService.class,
    BeanScannerController.class,
})
public class BeanScannerBackend {}