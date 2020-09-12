package nl.minfin.fiod.banktransactionsapi.domain

import nl.minfin.fiod.banktransactionsapi.banktransactions.ParseStatus
import org.axonframework.modelling.command.TargetAggregateIdentifier


data class CreateBankTransactionCommand(@TargetAggregateIdentifier val bankTransactionId: String, val bankTransaction: BankTransactionDto)
data class UpdateBankTransactionParseStatusCommand(@TargetAggregateIdentifier val bankTransactionId: String, val parseStatus: ParseStatus)

data class BankTransactionCreatedEvent(val bankTransactionId: String, val bankTransaction: BankTransactionDto)
data class UpdateBankTransactionParseStatusEvent(val bankTransactionId: String, val parseStatus: ParseStatus)
class AllBankTransactionsQuery