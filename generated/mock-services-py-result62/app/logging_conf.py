import logging
from logging.handlers import RotatingFileHandler
LOG_PATH = 'generated/mock-services-py-result62\mock-services.log'
def setup_logging():
    logger = logging.getLogger('mock-services')
    logger.setLevel(logging.INFO)
    handler = RotatingFileHandler(LOG_PATH, maxBytes=1048576, backupCount=3)
    fmt = logging.Formatter('%(asctime)s %(levelname)s %(message)s')
    handler.setFormatter(fmt)
    if not logger.handlers:
        logger.addHandler(handler)
