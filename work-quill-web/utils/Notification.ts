import Notiflix from "notiflix";

export const Notify = {
    info(message: string) {
        Notiflix.Notify.info(message, {
            clickToClose: true,
            position: "right-bottom",
        });
    },
    failure(message: string) {
        Notiflix.Notify.failure(message, {
            clickToClose: true,
            position: "right-bottom",
        });
    },
    success(message: string) {
        Notiflix.Notify.success(message, {
            clickToClose: true,
            position: "right-bottom",
        });
    },
};
