package nl.minfin.fiod.banktransactionsapi.domain

import nl.minfin.fiod.banktransactionsapi.banktransactions.ParseStatus
import org.axonframework.modelling.command.TargetAggregateIdentifier
import org.axonframework.serialization.Revision


data class CreateBankTransactionCommand(@TargetAggregateIdentifier val bankTransactionId: String, val bankTransaction: BankTransactionDto)
data class UpdateBankTransactionParseStatusCommand(@TargetAggregateIdentifier val bankTransactionId: String, val parseStatus: ParseStatus)

@Revision("2.0")
data class BankTransactionCreatedEvent(val bankTransactionId: String, val bankTransaction: BankTransactionDto)
data class UpdateBankTransactionParseStatusEvent(val bankTransactionId: String, val parseStatus: ParseStatus)
class AllBankTransactionsQuery