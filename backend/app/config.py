import os


class Settings:
    # Example configuration with sane defaults; extend as needed
    PROVIDER_URI_CRP10X1: str = os.environ.get("PROVIDER_URI_CRP10X1", "http://localhost:9001")
    PROVIDER_URI_CRP11X1: str = os.environ.get("PROVIDER_URI_CRP11X1", "http://localhost:9002")
    CORS_ORIGINS: str = os.environ.get("CORS_ORIGINS", "*")


settings = Settings()