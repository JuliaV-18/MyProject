package com.lian.myproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.lian.myproject.R;
import com.lian.myproject.model.Loan;
import com.lian.myproject.utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.ViewHolder> {


    public interface OnLoanClickListener {
        void onLoanClick(Loan loan);

        void onLongLoanClick(Loan loan);
    }

    private final List<Loan> loanList;
    private final LoanAdapter.OnLoanClickListener onLoanClickListener;

    public LoanAdapter(List<Loan> loanList, LoanAdapter.OnLoanClickListener onLoanClickListener) {
        this.loanList = loanList;
        this.onLoanClickListener = onLoanClickListener;
    }

    public LoanAdapter(@Nullable final LoanAdapter.OnLoanClickListener onLoanClickListener) {
        loanList = new ArrayList<>();
        this.onLoanClickListener = onLoanClickListener;
    }

    @NonNull
    @Override
    public LoanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loan, parent, false);
        return new LoanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoanAdapter.ViewHolder holder, int position) {
        Loan loan = loanList.get(position);
        if (loan == null) return;

        holder.tvTitle.setText(loan.getBookName());
        holder.tvBorrowDate.setText(loan.getBorrowDateString());
        holder.tvReturnDate.setText(loan.getReturnDateString());
        holder.tvUser.setText(loan.getUserId());




        holder.itemView.setOnClickListener(v -> {
            if (onLoanClickListener != null) {
                onLoanClickListener.onLoanClick(loan);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (onLoanClickListener != null) {
                onLoanClickListener.onLongLoanClick(loan);
            }
            return true;
        });

    }

    @Override
    public int getItemCount() {
        return loanList.size();
    }

    public void setLoanList(List<Loan> loans) {
        loanList.clear();
        loanList.addAll(loans);
        notifyDataSetChanged();
    }

    public void addLoan(Loan loan) {
        loanList.add(loan);
        notifyItemInserted(loanList.size() - 1);
    }

    public void updateLoan(Loan loan) {
        int index = loanList.indexOf(loan);
        if (index == -1) return;
        loanList.set(index, loan);
        notifyItemChanged(index);
    }

    public void removeLoan(Loan loan) {
        int index = loanList.indexOf(loan);
        if (index == -1) return;
        loanList.remove(index);
        notifyItemRemoved(index);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUser, tvTitle, tvBorrowDate, tvReturnDate;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_loan_book_name);
            tvBorrowDate = itemView.findViewById(R.id.tv_item_loan_borrow);
            tvReturnDate = itemView.findViewById(R.id.tv_item_loan_return);
            tvUser = itemView.findViewById(R.id.tv_loan_user_name);

        }
    }
}
