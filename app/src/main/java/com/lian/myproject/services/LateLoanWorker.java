package com.lian.myproject.services;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.lian.myproject.R;
import com.lian.myproject.model.Loan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;



public class LateLoanWorker extends Worker {



    public LateLoanWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {

        DatabaseService databaseService = DatabaseService.getInstance();

        CountDownLatch latch = new CountDownLatch(1);






            List<Loan> loans = new ArrayList<>();


            databaseService.getLateLoan(new DatabaseService.DatabaseCallback<List<Loan>>() {

                @Override
                public void onCompleted(List<Loan> loanList) {

                    loans.addAll(loanList);




                    for (Loan loan : loans) {

                            sendNotification(loan);
                        }


                    latch.countDown();

                }

                @Override
                public void onFailed(Exception e) {

                    latch.countDown();
                }
            });

            try {
                latch.await();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }


        return null;
    }







    private void sendNotification(Loan loan) {

        Context context = getApplicationContext();

        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "late_loans_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Late Loans",
                    NotificationManager.IMPORTANCE_HIGH
            );
            manager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setContentTitle("Late Book Return")
                .setContentText("Book \"" + loan.getBookName() + "\" is overdue!")
                .setSmallIcon(R.drawable.ic_launcher_foreground)

                .build();

        manager.notify(loan.getId().hashCode(), notification);
    }
}
